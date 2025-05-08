package supercoding.pj2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SellerProductResponseDto {
    private String message;
    private Long sellerItemId;
}
