package supercoding.pj2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductRequestSizeDto {

    @Schema(description = "신발 사이즈", example = "260")
    private String size;

    @Schema(description = "해당 사이즈 재고", example = "7")
    private int stock;
}