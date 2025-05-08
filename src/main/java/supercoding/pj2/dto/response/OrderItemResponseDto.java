package supercoding.pj2.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
public class OrderItemResponseDto {
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private int quantity;
}
