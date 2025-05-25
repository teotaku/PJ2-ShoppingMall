package supercoding.pj2.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.LoginRequestDto;
import supercoding.pj2.dto.request.SignupRequestDto;
import supercoding.pj2.dto.request.VerificationRequestDto;
import supercoding.pj2.dto.response.EmailCheckResponseDto;
import supercoding.pj2.dto.response.LoginResponseDto;
import supercoding.pj2.dto.response.OAuthResponseDto;
import supercoding.pj2.service.AuthService;
import supercoding.pj2.service.UserService;
import supercoding.pj2.service.VerificationService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final VerificationService verificationService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호 등으로 사용자 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰 발급")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃", description = "클라이언트 측에서 JWT를 제거하며, 서버는 단순 응답")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("로그아웃 완료");
    }

    @Operation(summary = "이메일 중복 확인", description = "사용 가능한 이메일인지 확인")
    @GetMapping("/check-email")
    public ResponseEntity<EmailCheckResponseDto> checkEmail(@RequestParam String email) {
        boolean available = userService.isEmailAvailable(email);
        return ResponseEntity.ok(new EmailCheckResponseDto(available));
    }

    @Operation(summary = "이메일 인증코드 발송", description = "사용자 이메일로 인증코드를 발송")
    @PostMapping("/verify-code")
    public ResponseEntity<String> sendVerificationCode(@RequestBody VerificationRequestDto request) {
        verificationService.sendCode(request);
        return ResponseEntity.ok("Verification code sent.");
    }

    @Operation(summary = "소셜 로그인", description = "Google, Kakao, Naver 등의 provider로 OAuth 로그인")
    @GetMapping("/{provider}")
    public ResponseEntity<OAuthResponseDto> oauthLogin(
            @PathVariable String provider,
            @RequestParam("code") String code
    ) {
        OAuthResponseDto response = authService.login(provider, code);
        return ResponseEntity.ok(response);
    }
}
