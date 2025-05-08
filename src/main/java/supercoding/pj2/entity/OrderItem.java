package supercoding.pj2.entity;


import jakarta.persistence.*;
import lombok.*;
import supercoding.pj2.dto.response.OrderItemResponseDto;
import supercoding.pj2.dto.response.ProductResponseDto;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_id")
    private Long productId;
    @Column(nullable = false)
    private String name;
    @Column
    private String imageUrl;
    @Column
    private String color;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private BigDecimal price;

    public OrderItemResponseDto toDto() {
        return OrderItemResponseDto.builder()
                .name(this.name)
                .imageUrl(this.imageUrl)
                .price(this.price)
                .quantity(this.quantity)
                .color(color)
                .build();
    }


}
