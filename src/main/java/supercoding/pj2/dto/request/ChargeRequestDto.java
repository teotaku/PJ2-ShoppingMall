package supercoding.pj2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ChargeRequestDto {
    @Schema(description = "충전 금액", example = "10000")
    private Long amount;
}
