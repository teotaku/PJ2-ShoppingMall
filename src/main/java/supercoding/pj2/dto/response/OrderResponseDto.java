package supercoding.pj2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private Long orderId;
    private String shippingAddress;
    private List<OrderItemResponseDto> items;
}



