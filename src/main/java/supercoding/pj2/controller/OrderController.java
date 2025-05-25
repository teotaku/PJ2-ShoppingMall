package supercoding.pj2.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.OrderRequestDto;
import supercoding.pj2.dto.response.OrderItemResponseDto;
import supercoding.pj2.dto.response.OrderResponseDto;
import supercoding.pj2.service.OrderService;
import supercoding.pj2.service.UserService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

//    //주문생성
//    @Operation(summary = "주문 생성",description = "파라미터로 주문dto 받고 주문 생성")
//    @PostMapping
//    public ResponseEntity<Void> createdOrder(@AuthenticationPrincipal UserDetails userDetails,
//                                             @RequestBody OrderRequestDto orderRequestDto) {
//        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
//        orderService.createOrder(userId, orderRequestDto.getItems(), orderRequestDto.getShippingAddress());
//        return ResponseEntity.ok().build();
//
//    }


    //주문조회
    @GetMapping
    @Operation(summary = "주문 조회",description = "파라미터로 pageable 받고 주문 조회")
    public ResponseEntity<Page<OrderResponseDto>> getOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        Page<OrderResponseDto> result = orderService.getOrders(userId, pageable);
        return ResponseEntity.ok(result);
    }

}
