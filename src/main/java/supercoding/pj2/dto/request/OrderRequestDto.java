package supercoding.pj2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import supercoding.pj2.entity.CartItem;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderRequestDto {

    private String shippingAddress;
    private List<CartItem> items;
}
