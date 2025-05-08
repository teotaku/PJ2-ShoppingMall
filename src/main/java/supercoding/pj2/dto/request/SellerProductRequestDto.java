package supercoding.pj2.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerProductRequestDto {
    private Long userId;
    private Long productId;
    private int stock;
}