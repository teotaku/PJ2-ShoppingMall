package supercoding.pj2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChargeRequestDto {
    @Schema(description = "충전 금액", example = "10000")
    private Long amount;
}
