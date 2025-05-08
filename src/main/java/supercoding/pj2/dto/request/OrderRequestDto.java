package supercoding.pj2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import supercoding.pj2.entity.CartItem;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderRequestDto {

    @Schema(description = "배송지 주소", example = "서울특별시 강남구 테헤란로 123")
    private String shippingAddress;
    @Schema(description = "주문할 장바구니 상품 목록")
    private List<CartItemRequestDto> items;
}
