package supercoding.pj2.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.ChargeRequestDto;
import supercoding.pj2.dto.request.UserRequestDto;
import supercoding.pj2.dto.response.UserResponseDto;
import supercoding.pj2.service.MyPageService;
import supercoding.pj2.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;

    //내 정보 조회
    @GetMapping
    @Operation(summary = "마이페이지 내 정보 조회")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(myPageService.getMyInfo(userId));
    }

    //내 정보 수정
    @PutMapping
    @Operation(summary = "마이페이지 내 정보 수정",description = "파라미터로 정보수정DTO(UserRequestDto)를 받고 정보수정")
    public ResponseEntity<Void> updateMyInfo(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody UserRequestDto dto) {
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        myPageService.updateMyInfo(userId, dto);
        return ResponseEntity.ok().build();
    }

    //페이 조회
    @GetMapping("/{userId}/balance")
    @Operation(summary = "페이 잔액 조회")
    public ResponseEntity<Long> getPayBalance(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(myPageService.getPayBalance(userId));
    }

    //페이 충전
    @Operation(summary = "페이 충전",description = "파라미터로 페이충전 DTO(ChargeRequestDto)를 받고 페이충전")
    @PostMapping("/{userId}/charge")
    public ResponseEntity<Void> chargePay(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody ChargeRequestDto dto) {
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        myPageService.chargePay(userId, dto.getAmount());
        return ResponseEntity.ok().build();
    }
}
