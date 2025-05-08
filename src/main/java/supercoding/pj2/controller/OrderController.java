package supercoding.pj2.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.OrderRequestDto;
import supercoding.pj2.dto.response.OrderItemResponseDto;
import supercoding.pj2.dto.response.OrderResponseDto;
import supercoding.pj2.service.OrderService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문생성
    @Operation(summary = "주문 생성",description = "파라미터로 주문dto 받고 주문 생성")
    @PostMapping
    public ResponseEntity<Void> createdOrder(@RequestParam Long userId,
                                             @RequestBody OrderRequestDto orderRequestDto) {
        orderService.createOrder(userId, orderRequestDto.getItems(), orderRequestDto.getShippingAddress());
        return ResponseEntity.ok().build();

    }


    //주문조회
    @GetMapping
    @Operation(summary = "주문 조회",description = "파라미터로 pageable 받고 주문 조회")
    public ResponseEntity<Page<OrderResponseDto>> getOrders(
            @RequestParam Long userId,
            Pageable pageable) {
        Page<OrderResponseDto> result = orderService.getOrders(userId, pageable);
        return ResponseEntity.ok(result);
    }

}
