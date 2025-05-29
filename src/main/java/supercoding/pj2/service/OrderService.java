package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supercoding.pj2.dto.request.CartItemRequestDto;
import supercoding.pj2.dto.response.OrderItemResponseDto;
import supercoding.pj2.dto.response.OrderResponseDto;
import supercoding.pj2.entity.*;
import supercoding.pj2.exception.NotFoundException;
import supercoding.pj2.repository.OrderItemRepository;
import supercoding.pj2.repository.OrderRepository;
import supercoding.pj2.repository.ProductRepository;
import supercoding.pj2.repository.ProductSizeRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductSizeRepository productSizeRepository;
    //주문생성
    public void createOrder(Long userId, List<CartItemRequestDto> items, String shippingAddress) {
        Order order = Order.builder()
                .userId(userId)
                .shippingAddress(shippingAddress)
                .status(Status.PENDING)
                .build();

        orderRepository.save(order);
        log.info("Order ID: {}", order.getId());

        List<Long> productIds = items.stream()
                .map(CartItemRequestDto::getProductId)
                .distinct()
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<OrderItem> orderItems = items.stream()
                .map(item -> {
                    Product product = productMap.get(item.getProductId());

                    if(product == null) throw new NotFoundException("상품을 찾을 수 없습니다." + item.getProductId());

                    ProductSize productSize = productSizeRepository.findByProductIdAndSize(item.getProductId(), item.getSize())
                            .orElseThrow(() -> new NotFoundException("상품(사이즈정보)를 찾을 수 없습니다"));

                    return OrderItem.builder()
                            .orderId(order.getId())
                            .productId(product.getId())
                            .name(product.getName())
                            .quantity(item.getQuantity())
                            .imageUrl(product.getImageUrl())
                            .color(product.getColor())
                            .price(product.getPrice())
                            .size(productSize.getSize())
                            .build();
                }).toList();
        orderItemRepository.saveAll(orderItems);
    }


    //주문 내역
    @Transactional(readOnly = true)
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
