package supercoding.pj2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SellerProductListResponseDto {
    private Long productId;
    private String name;
    private int price;
    private int stock;
}
