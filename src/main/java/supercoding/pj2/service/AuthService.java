package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import supercoding.pj2.dto.request.LoginRequest;
import supercoding.pj2.dto.request.SignupRequest;
import supercoding.pj2.dto.response.LoginResponse;
import supercoding.pj2.entity.User;
import supercoding.pj2.exception.UnauthorizedException;
import supercoding.pj2.repository.UserRepository;
import supercoding.pj2.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setGender(User.Gender.valueOf(request.getGender()));
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("이메일 또는 비밀번호가 틀렸습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("이메일 또는 비밀번호가 틀렸습니다.");
        }

        String token = jwtProvider.generateToken(user.getId(), user.getEmail(), user.getProvider().name());
        return new LoginResponse(token);
    }
}
