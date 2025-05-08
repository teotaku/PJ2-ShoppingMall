package supercoding.pj2.dto.request;


import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class CartItemRequestDto {

    private Long productId;
    private int quantity;
    private BigDecimal price;

}
