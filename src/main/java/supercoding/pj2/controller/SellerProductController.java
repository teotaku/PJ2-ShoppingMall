package supercoding.pj2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.SellerProductRequestDto;
import supercoding.pj2.dto.response.SellerProductListResponseDto;
import supercoding.pj2.dto.response.SellerProductResponseDto;
import supercoding.pj2.security.JwtProvider;
import supercoding.pj2.service.SellerProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller/products")
@RequiredArgsConstructor
public class SellerProductController {

    private final SellerProductService sellerProductService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<SellerProductResponseDto> registerProduct(
            @RequestBody SellerProductRequestDto request
    ) {
        SellerProductResponseDto response = sellerProductService.registerProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SellerProductListResponseDto>> getSellerProducts(
            @RequestHeader("Authorization") String token
    ) {
        Long userId = jwtProvider.getUserIdFromToken(token); // JWT에서 판매자 ID 추출
        List<SellerProductListResponseDto> products = sellerProductService.getSellerProducts(userId);
        return ResponseEntity.ok(products);
    }

}
