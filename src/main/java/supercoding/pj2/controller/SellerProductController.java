package supercoding.pj2.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "상품 등록", description = "판매자가 새로운 상품을 등록합니다.")
    @PostMapping
    public ResponseEntity<SellerProductResponseDto> registerProduct(
            @RequestBody SellerProductRequestDto request
    ) {
        SellerProductResponseDto response = sellerProductService.registerProduct(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "판매자 상품 목록 조회", description = "JWT 토큰을 통해 판매자 본인의 등록 상품 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<SellerProductListResponseDto>> getSellerProducts(
            @RequestHeader("Authorization") String token
    ) {
        Long userId = jwtProvider.getUserIdFromToken(token); // JWT에서 판매자 ID 추출
        List<SellerProductListResponseDto> products = sellerProductService.getSellerProducts(userId);
        return ResponseEntity.ok(products);
    }

}
