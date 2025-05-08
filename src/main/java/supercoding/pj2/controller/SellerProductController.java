package supercoding.pj2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.SellerProductRequestDto;
import supercoding.pj2.dto.response.SellerProductResponseDto;
import supercoding.pj2.service.SellerProductService;

@RestController
@RequestMapping("/api/v1/seller/products")
@RequiredArgsConstructor
public class SellerProductController {

    private final SellerProductService sellerProductService;

    @PostMapping
    public ResponseEntity<SellerProductResponseDto> registerProduct(
            @RequestBody SellerProductRequestDto request
    ) {
        SellerProductResponseDto response = sellerProductService.registerProduct(request);
        return ResponseEntity.ok(response);
    }
}
