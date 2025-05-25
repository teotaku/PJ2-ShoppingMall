package supercoding.pj2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationRequestDto {

    @Schema(description = "인증 유형 (이메일 또는 전화번호)", example = "email")
    private String type;
    @Schema(description = "인증 대상 (이메일 주소 또는 전화번호)", example = "user@example.com")
    private String destination;
}