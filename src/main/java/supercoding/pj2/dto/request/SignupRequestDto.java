package supercoding.pj2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import supercoding.pj2.entity.User;

@Getter
@Setter
public class SignupRequestDto {

    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "password1234")
    private String password;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    @Schema(description = "전화번호", example = "01012345678")
    private String phone;

    @Schema(description = "주소", example = "서울특별시 강남구 역삼동")
    private String address;

    @Schema(description = "성별 (M 또는 F)", example = "M")
    private String gender;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .phone(this.phone)
                .address(this.address)
                .gender(User.Gender.valueOf(this.gender)) // enum 변환
                .build();
    }
}
