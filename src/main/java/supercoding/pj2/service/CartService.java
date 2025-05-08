package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supercoding.pj2.dto.request.CartItemRequestDto;
import supercoding.pj2.dto.response.CartItemResponseDto;
import supercoding.pj2.entity.Cart;
import supercoding.pj2.entity.CartItem;
import supercoding.pj2.entity.Product;
import supercoding.pj2.repository.CartItemRepository;
import supercoding.pj2.repository.CartRepository;
import supercoding.pj2.repository.OrderRepository;
import supercoding.pj2.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;
    private final CartRepository cartRepository;

    //장바구니에 상품 추가
    public void addItem(Long userId, CartItemRequestDto dto) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() ->
                cartRepository.save
                        (Cart.builder()
                                .userId(userId)
                                .build()));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));


        CartItem item = CartItem.builder()
                .cartId(cart.getCartId())
                .productId(product.getId())
                .price(product.getPrice())
                .quantity(dto.getQuantity())
                .build();
        cartItemRepository.save(item);

    }
    @Transactional(readOnly = true)
    //장바구니 물품 전체 조회 페이징처리
    public Page<CartItemResponseDto> getCartItems(Long userId, Pageable pageable) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("장바구니가 없습니다"));

        List<CartItem> cartItemList = cartItemRepository.findByCartId(cart.getCartId());

        List<Long> productIds = cartItemList.stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<CartItemResponseDto> dtoList = cartItemList.stream()
                .map(item -> {
                    Product product = productMap.get(item.getProductId());
                    return CartItemResponseDto.builder()
                            .productId(product.getId())
                            .name(product.getName())
                            .color(product.getColor())
                            .imageUrl(product.getImageUrl())
                            .price(product.getPrice())
                            .quantity(item.getQuantity())
                            .build();
                }).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, cartItemList.size());
    }
    //장바구니 항목 수량 수정
    public void updateQuantity(Long itemId, int quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("장바구니 항목이 없습니다."));
        item.updateQuantity(quantity);
    }
    //장바구니 항목 삭제
    public void deleteItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    //장바구니 결제 처리: 재고 감소 + 장바구니 비움
    public void checkout(Long userId,String shippingAddress) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("장바구니가 없습니다."));

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        for (CartItem item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("상품 재고가 부족합니다." + product.getName());
            }
            product.decreaseStock(item.getQuantity()); //재고차감
        }

        //주문생성
        orderService.createOrder(userId, items, shippingAddress);


        cartItemRepository.deleteAll(items); //결제후 장바구니 전부 비움.
    }


}

