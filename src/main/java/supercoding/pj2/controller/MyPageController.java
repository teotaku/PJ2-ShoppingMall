package supercoding.pj2.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.ChargeRequestDto;
import supercoding.pj2.dto.request.UserRequestDto;
import supercoding.pj2.dto.response.UserResponseDto;
import supercoding.pj2.service.MyPageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    //내 정보 조회
    @GetMapping("/{userId}")
    @Operation(summary = "마이페이지 내 정보 조회")
    public ResponseEntity<UserResponseDto> getMyInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(myPageService.getMyInfo(userId));
    }

    //내 정보 수정
    @PutMapping("/{userId}")
    @Operation(summary = "마이페이지 내 정보 수정",description = "파라미터로 정보수정DTO(UserRequestDto)를 받고 정보수정")
    public ResponseEntity<Void> updateMyInfo(@PathVariable Long userId,
                                             @RequestBody UserRequestDto dto) {
        myPageService.updateMyInfo(userId, dto);
        return ResponseEntity.ok().build();
    }

    //페이 조회
    @GetMapping("/{userId}/balance")
    @Operation(summary = "페이 잔액 조회")
    public ResponseEntity<Long> getPayBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(myPageService.getPayBalance(userId));
    }

    //페이 충전
    @Operation(summary = "페이 충전",description = "파라미터로 페이충전 DTO(ChargeRequestDto)를 받고 페이충전")
    @PostMapping("/{userId}/charge")
    public ResponseEntity<Void> chargePay(@PathVariable Long userId,
                                          @RequestBody ChargeRequestDto dto) {
        myPageService.chargePay(userId, dto.getAmount());
        return ResponseEntity.ok().build();
    }
}
