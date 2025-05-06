package supercoding.pj2.entity;

import jakarta.persistence.*;
import lombok.*;
import supercoding.pj2.dto.request.ProductRequestDto;
import supercoding.pj2.dto.response.ProductResponseDto;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private BigDecimal price;

    private Integer stock;

    @Column(name = "category_id")
    private Long categoryId;

    @Builder.Default
    private int viewCount = 0; //조회수
    @Builder.Default
    private int purchaseCount = 0; //구매수

    public int getPopularityScore() { //인기수 조회 << 조회수1 구매수5 비율로 설정.
        return this.viewCount + (purchaseCount * 5);
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increasePurchaseCount() {
        this.purchaseCount++;
    }



    //상품 수정 메서드
    public void update(ProductRequestDto dto) {
        if(dto.getName() != null) this.name = dto.getName();
        if(dto.getPrice() != null) this.price = dto.getPrice();
        if(dto.getStock() != null) this.stock = dto.getStock();
        if(dto.getDescription() != null)this.description = dto.getDescription();
        if(dto.getCategoryId() != null)this.categoryId = dto.getCategoryId();
    }

    public ProductResponseDto toDto() {
        return ProductResponseDto.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .description(this.description)
                .imageUrl(this.imageUrl)
                .viewCount(this.viewCount)
                .purchaseCount(this.purchaseCount)
                .popularityScore(this.getPopularityScore())
                .build();
    }


    public void decreaseStock(int quantity) {
        this.stock -= quantity;
    }
}