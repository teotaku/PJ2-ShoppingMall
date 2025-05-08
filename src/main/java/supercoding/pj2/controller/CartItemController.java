package supercoding.pj2.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "장바구니 전체 조회",description = "장바구니 물품 전체 조회")
    @GetMapping("/items")
    public ResponseEntity<Page<CartItemResponseDto>> getCart(@RequestParam Long userId, Pageable pageable) {
        return ResponseEntity.ok(cartService.getCartItems(userId, pageable));
    }

    //장바구니에 항목 추가
    @Operation(summary = "장바구니에 항목 추가",description = "유저아이디와 아이템DTO를 받고 장바구니에 항목 추가")
    @PostMapping("/items")
    public ResponseEntity<Void> addItem(@RequestParam Long userId, @RequestBody CartItemRequestDto dto) {
        cartService.addItem(userId, dto);
        return ResponseEntity.ok().build();
    }

    //장바구니 항목 수량 수정
    @Operation(summary = "장바구니 항목 수량 수정",description = "아이템 아이디와 수량을 받아서 수량 변경")
    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> updateItem(@PathVariable Long itemId, @RequestParam int quantity) {
        cartService.updateQuantity(itemId, quantity);
        return ResponseEntity.ok().build();
    }

    //장바구니 항목 삭제
    @Operation(summary = "장바구니 항목 삭제",description = "아이템 아이디를 받아서 장바구니항목 삭제 처리")
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        cartService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

    //장바구니 결제 처리
    @Operation(summary = "장바구니 결제",description = "유저 아이디와 주소지를 받아서 로직처리")
    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(@RequestParam Long userId,String shippingAddress) {
        cartService.checkout(userId,shippingAddress);
        return ResponseEntity.ok().build();
    }
}