package supercoding.pj2.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import supercoding.pj2.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ProductRequestDto {

    @Schema(description = "상품 이름", example = "조던1 레트로")
    private String name;

    @Schema(description = "상품 가격", example = "139000.00")
    private BigDecimal price;

    @Schema(description = "카테고리 ID", example = "2L")
    private Long categoryId;

    @Schema(description = "상품 설명", example = "레트로 디자인의 인기 조던 시리즈")
    private String description;

    @Schema(description = "색상", example = "Black/Red")
    private String color;

    @Schema(description = "이미지 URL", example = "https://cdn.example.com/images/jordan1.jpg")
    private String imageUrl;

    @Schema(description = "사이즈 및 재고 리스트")
    private List<ProductRequestSizeDto> sizes;


    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
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
