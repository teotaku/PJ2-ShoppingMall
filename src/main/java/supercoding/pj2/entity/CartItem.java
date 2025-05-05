package supercoding.pj2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart_items")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cart_id")
    private Long cartId;
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    private BigDecimal price;

    public void updateQuantity(int quantity) {
        this.quantity = quantity; //수량 변경.
    }

}
