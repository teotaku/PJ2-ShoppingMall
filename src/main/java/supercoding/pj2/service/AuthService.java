package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import supercoding.pj2.client.OAuthClient;
import supercoding.pj2.client.provider.GoogleOAuthClient;
import supercoding.pj2.client.provider.KaKaoOAuthClient;
import supercoding.pj2.client.provider.NaverOAuthClient;
import supercoding.pj2.dto.request.LoginRequestDto;
import supercoding.pj2.dto.request.SignupRequestDto;
import supercoding.pj2.dto.response.LoginResponseDto;
import supercoding.pj2.dto.response.OAuthResponseDto;
import supercoding.pj2.entity.User;
import supercoding.pj2.exception.UnauthorizedException;
import supercoding.pj2.repository.UserRepository;
import supercoding.pj2.security.JwtProvider;
import supercoding.pj2.userinfo.OAuthUserInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final GoogleOAuthClient googleOAuthClient;
    private final KaKaoOAuthClient kakaoOAuthClient;
    private final NaverOAuthClient naverOAuthClient;

    public void signup(SignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        User user = User.create(request, passwordEncoder);
        userRepository.save(user);
    }


    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("이메일 또는 비밀번호가 틀렸습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("이메일 또는 비밀번호가 틀렸습니다.");
        }

        String token = jwtProvider.generateToken(user.getId(), user.getEmail(), user.getProvider().name());
        return new LoginResponseDto(token);
    }

    public OAuthResponseDto login(String provider, String code) {
        // 1. access token 받아오기
        String accessToken = getOAuthClient(provider).getAccessToken(code);

        // 2. 사용자 정보 받아오기
        OAuthUserInfo userInfo = getOAuthClient(provider).getUserInfo(accessToken);

        // 3. 회원 DB 확인 또는 등록
        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> userRepository.save(userInfo.toEntity()));

        // 4. JWT 발급
        String token = jwtProvider.createToken(
                user.getEmail(),
                String.valueOf(user.getRole()) // 또는 user.getRole().name()
        );


        return new OAuthResponseDto(token, user);
    }

    private OAuthClient getOAuthClient(String provider) {
        return switch (provider.toUpperCase()) {
            case "GOOGLE" -> googleOAuthClient;
            case "KAKAO" -> kakaoOAuthClient;
            case "NAVER" -> naverOAuthClient;
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다: " + provider);
        };
    }
}

