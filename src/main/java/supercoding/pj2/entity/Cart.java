package supercoding.pj2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;

    @Column(name = "cart_id")
    private Long cartId;
    @Column(name = "user_id")
    private Long userId;



}
