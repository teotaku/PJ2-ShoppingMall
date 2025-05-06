package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import supercoding.pj2.dto.response.OrderItemResponseDto;
import supercoding.pj2.dto.response.OrderResponseDto;
import supercoding.pj2.entity.*;
import supercoding.pj2.repository.OrderItemRepository;
import supercoding.pj2.repository.OrderRepository;
import supercoding.pj2.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    //주문생성
    public void createOrder(Long userId, List<CartItem> items ,String shippingAddress) {
        Order order = Order.builder()
                .userId(userId)
                .shippingAddress(shippingAddress)
                .status(Status.PENDING)
                .build();

        orderRepository.save(order);

        List<Long> productIds = items.stream()
                .map(CartItem::getProductId)
                .distinct()
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<OrderItem> orderItems = items.stream()
                .map(item -> {
                    Product product = productMap.get(item.getProductId());
                    return OrderItem.builder()
                            .orderId(order.getId())
                            .productId(product.getId())
                            .quantity(item.getQuantity())
                            .price(product.getPrice())
                            .build();
                }).toList();
        orderItemRepository.saveAll(orderItems);
    }




    public Page<OrderResponseDto> getOrders(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        if (orders.isEmpty()) {
            Page.empty(pageable);
        }
        //order id 주문 id 리스트 뽑기
        List<Long> orderIds = orders.stream()
                .map(Order::getId)
                .toList();
        //orderitem뽑기
        List<OrderItem> orderItemList = orderItemRepository.findByOrderIdIn(orderIds);

        //orderid 기준으로 orderitem 집어넣기.
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderId));


        //Order->OrderREsponseDTo 변환
        List<OrderResponseDto> result = orders.stream().map(order -> {

            List<OrderItemResponseDto> items = orderItemMap.getOrDefault(order.getId(), List.of()).stream()
                    .map(OrderItem::toDto)
                    .toList();

            return OrderResponseDto.builder()
                    .orderId(order.getId())
                    .shippingAddress(order.getShippingAddress())
                    .items(items)
                    .build();
        }).toList();
        return new PageImpl<>(result, pageable, orders.getTotalElements());









    }

}
