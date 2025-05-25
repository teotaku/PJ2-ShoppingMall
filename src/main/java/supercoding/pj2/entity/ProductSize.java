package supercoding.pj2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
@Entity
public class ProductSize {
    @Id
    @GeneratedValue
    private Long id;

    private Long productId; // 연관관계 없이 ID만

    private String size;
    private int stock;

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) throw new IllegalArgumentException("재고 부족");
        this.stock -= quantity;
    }
}