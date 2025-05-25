package supercoding.pj2.entity;

import jakarta.persistence.*;
import lombok.*;
import supercoding.pj2.dto.request.SellerProductRequestDto;

@Entity
@Table(name = "seller_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerItemId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int stock;

    public static SellerItem createFrom(SellerProductRequestDto dto) {
        return SellerItem.builder()
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .stock(dto.getStock())
                .build();
    }

}
