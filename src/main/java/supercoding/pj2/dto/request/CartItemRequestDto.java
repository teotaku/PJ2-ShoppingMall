package supercoding.pj2.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class CartItemRequestDto {
    @Schema(description = "상품 ID", example = "1")
    private Long productId;
    @Schema(description = "수량", example = "3")
    private int quantity;
    @Schema(description = "상품 사이즈", example = "260")
    private String size;

}
