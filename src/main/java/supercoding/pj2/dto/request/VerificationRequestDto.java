package supercoding.pj2.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationRequestDto {
    private String type;         // "email" or "phone"
    private String destination;  // 이메일 또는 전화번호
}
