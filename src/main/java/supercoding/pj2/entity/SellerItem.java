package supercoding.pj2.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "seller_items")
public class SellerItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_id")
    private Long productId;

    private int stock;

}
