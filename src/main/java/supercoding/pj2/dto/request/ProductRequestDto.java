package supercoding.pj2.dto.request;


import lombok.Getter;
import supercoding.pj2.entity.Product;

import java.math.BigDecimal;

@Getter
public class ProductRequestDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private String description;
    private String imageUrl;
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
}
