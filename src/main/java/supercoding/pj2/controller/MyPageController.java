package supercoding.pj2.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import supercoding.pj2.dto.request.ChargeRequestDto;
import supercoding.pj2.dto.request.UserRequestDto;
import supercoding.pj2.dto.response.UserResponseDto;
import supercoding.pj2.s3.S3Uploader;
import supercoding.pj2.security.CustomUserDetails;
import supercoding.pj2.service.MyPageService;
import supercoding.pj2.service.UserService;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    //내 정보 조회
    @GetMapping
    @Operation(summary = "마이페이지 내 정보 조회")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(myPageService.getMyInfo(userId));
    }

    //내 정보 수정
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "마이페이지 내 정보 수정",description = "파라미터로 정보수정DTO(UserRequestDto)를 받고 정보수정")
    public ResponseEntity<Void> updateMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestPart("dto") UserRequestDto dto,
                                             @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        Long userId = userService.findByEmail(userDetails.getUsername()).getId();


        if (image != null) {
            String imageUrl = s3Uploader.upload(image);
            dto.setProfileImageUrl(imageUrl); //주입 메서드로 넣기
        }

        myPageService.updateMyInfo(userId, dto);
        return ResponseEntity.ok().build();
    }

    //페이 조회
    @GetMapping("/balance")
    @Operation(summary = "페이 잔액 조회")
    public ResponseEntity<Long> getPayBalance(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(myPageService.getPayBalance(userId));
    }

    //페이 충전
    @Operation(summary = "페이 충전",description = "파라미터로 페이충전 DTO(ChargeRequestDto)를 받고 페이충전")
    @PostMapping("/charge")
    public ResponseEntity<Void> chargePay(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestBody ChargeRequestDto dto) {
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        myPageService.chargePay(userId, dto.getAmount());
        return ResponseEntity.ok().build();
    }
}
