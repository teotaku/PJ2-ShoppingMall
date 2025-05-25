package supercoding.pj2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class CartItemResponseDto {

    private Long id;
    private Long productId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String color;
    private String imageUrl;
    private String size;


}
