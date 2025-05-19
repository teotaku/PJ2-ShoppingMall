package supercoding.pj2.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import supercoding.pj2.entity.Product;

import java.math.BigDecimal;

@Getter
public class ProductRequestDto {

    @Schema(description = "상품 이름", example = "무선 키보드")
    private String name;
    @Schema(description = "상품 가격", example = "24900.00")
    private BigDecimal price;
    @Schema(description = "상품 재고", example = "100")
    private Integer stock;
    @Schema(description = "카테고리 ID", example = "2")
    private Long categoryId;
    @Schema(description = "상품 설명", example = "저소음 무선 키보드입니다.")
    private String description;
    @Schema(description = "이미지 URL", example = "https://cdn.example.com/images/keyboard.jpg")
    private String imageUrl;
    @Schema(description = "색상", example = "Black")
    private String color;



    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .categoryId(categoryId)
                .description(description)
                .imageUrl(imageUrl)
                .color(color)
                .build();

    }
    public void injectImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
