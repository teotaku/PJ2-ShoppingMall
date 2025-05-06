package supercoding.pj2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.LoginRequestDto;
import supercoding.pj2.dto.request.SignupRequestDto;
import supercoding.pj2.dto.response.EmailCheckResponseDto;
import supercoding.pj2.dto.response.LoginResponseDto;
import supercoding.pj2.service.AuthService;
import supercoding.pj2.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails userDetails) {
        // 실제 JWT 무효화는 클라이언트에서 처리하므로, 서버는 응답만
        return ResponseEntity.ok("로그아웃 완료");
    }

    @GetMapping("/check-email")
    public ResponseEntity<EmailCheckResponseDto> checkEmail(@RequestParam String email) {
        boolean available = userService.isEmailAvailable(email);
        return ResponseEntity.ok(new EmailCheckResponseDto(available));
    }
}
