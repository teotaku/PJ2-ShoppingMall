package supercoding.pj2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
public class RecentOrderDto {

    private String productName;
    private String imageUrl;
    private String color;
    private int quantity;
    private BigDecimal price;
    private String size;

}
