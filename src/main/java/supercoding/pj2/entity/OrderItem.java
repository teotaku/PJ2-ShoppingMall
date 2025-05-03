package supercoding.pj2.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_id")
    private Long productId;

    private int quantity;

    private BigDecimal price;


}
