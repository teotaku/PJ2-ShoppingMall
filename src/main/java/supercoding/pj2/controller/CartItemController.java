package supercoding.pj2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.CartItemRequestDto;
import supercoding.pj2.dto.response.CartItemResponseDto;
import supercoding.pj2.service.CartService;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartItemController {

    private CartService cartService;

    //장바구니 전체조회
    @GetMapping("/items")
    public ResponseEntity<Page<CartItemResponseDto>> getCart(@RequestParam Long userId, Pageable pageable) {
        return ResponseEntity.ok(cartService.getCartItems(userId, pageable));
    }

    //장바구니에 항목 추가
    @PostMapping("/items")
    public ResponseEntity<Void> addItem(@RequestParam Long userId, @RequestBody CartItemRequestDto dto) {
        cartService.addItem(userId, dto);
        return ResponseEntity.ok().build();
    }

    //장바구니 항목 수량 수정
    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> updateItem(@PathVariable Long itemId, @RequestParam int quantity) {
        cartService.updateQuantity(itemId, quantity);
        return ResponseEntity.ok().build();
    }

    //장바구니 항목 삭제
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        cartService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

    //장바구니 결제 처리
    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(@RequestParam Long userId,String shippingAddress) {
        cartService.checkout(userId,shippingAddress);
        return ResponseEntity.ok().build();
    }
}