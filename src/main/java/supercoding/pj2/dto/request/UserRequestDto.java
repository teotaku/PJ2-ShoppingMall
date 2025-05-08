package supercoding.pj2.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserRequestDto {
    @Schema(description = "이메일", example = "user@example.com")
    private String email;
    @Schema(description = "배송지 주소", example = "서울특별시 송파구 가락동 123")
    private String shippingAddress;
    @Schema(description = "프로필 이미지 URL", example = "https://cdn.example.com/profile.jpg")
    private String profileImageUrl;
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;




}
