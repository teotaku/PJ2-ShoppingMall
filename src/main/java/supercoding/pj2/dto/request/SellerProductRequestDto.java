package supercoding.pj2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerProductRequestDto {

    @Schema(description = "판매자 ID", example = "1")
    private Long userId;
    @Schema(description = "상품 ID", example = "1001")
    private Long productId;
    @Schema(description = "재고 수량", example = "50")
    private int stock;
}