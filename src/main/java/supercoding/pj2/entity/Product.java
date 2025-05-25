package supercoding.pj2.entity;

import jakarta.persistence.*;
import lombok.*;
import supercoding.pj2.dto.request.ProductRequestDto;
import supercoding.pj2.dto.response.ProductResponseDto;
import supercoding.pj2.dto.response.ProductResponseSizeDto;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

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


    @Column(name = "category_id")
    @Builder.Default
    private Long categoryId = 2L;


    @Column
    @Builder.Default
    private String color = "사진참고";

    @Builder.Default
    private int viewCount = 0; //조회수
    @Builder.Default
    private int purchaseCount = 0; //구매수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Provider provider = Provider.LOCAL;

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
        if(dto.getDescription() != null)this.description = dto.getDescription();
        if(dto.getCategoryId() != null)this.categoryId = dto.getCategoryId();
    }

    public ProductResponseDto toDto(List<ProductSize> sizes) {
        return ProductResponseDto.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .description(this.description)
                .imageUrl(this.imageUrl)
                .viewCount(this.viewCount)
                .purchaseCount(this.purchaseCount)
                .popularityScore(this.getPopularityScore())
                .sizes(sizes.stream()
                        .map(s -> new ProductResponseSizeDto(s.getSize(),s.getStock()))
                        .toList())
                .build();
    }

}