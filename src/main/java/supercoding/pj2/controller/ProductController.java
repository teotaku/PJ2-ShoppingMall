package supercoding.pj2.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import supercoding.pj2.dto.request.ProductRequestDto;
import supercoding.pj2.dto.request.ProductSearchCondition;
import supercoding.pj2.dto.response.ProductResponseDto;
import supercoding.pj2.s3.S3Uploader;
import supercoding.pj2.service.ProductService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final S3Uploader s3Uploader;

    //전체상품조회
    @GetMapping
    @Operation(summary = "전체 상품 조회",description = "파라미터로 pageable 받고 전체상품 조회")
    public ResponseEntity<Page<ProductResponseDto>> getAll(@ParameterObject ProductSearchCondition condition) {
        return ResponseEntity.ok(productService.getAllProducts(condition));
    }


    //카테고리 조회
    @GetMapping("/category")
    @Operation(summary = "상품 카테고리로 조회",description = "파라미터로 pageable(카테고리포함) 받고 카테고리별 상품 조회")
    public ResponseEntity<Page<ProductResponseDto>> getByCategoryPaged(@ParameterObject ProductSearchCondition condition) {
        return ResponseEntity.ok(productService.getByCategory(condition));
    }

    //키워드로 조회
    @GetMapping("/search")
    @Operation(summary = "상품 키워드 조회",description = "파라미터로 pageable(키워드포함) 받고 키워드포함 상품 조회")
    public ResponseEntity<Page<ProductResponseDto>> getByKeyword(@ParameterObject ProductSearchCondition condition) {
        return ResponseEntity.ok(productService.findByKeyword(condition));
    }

    //아이디로 단건 조회
    @GetMapping("/{id}")
    @Operation(summary = "상품 단건 조회",description = "파라미터로 상품id 받고 상품 단건 조회")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    //상품 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "상품 등록",description = "이미지와 상품 정보를 함께 받아 상품을 등록합니다.")
    public ResponseEntity<Void> create(@RequestPart("dto") ProductRequestDto dto,
                                       @RequestPart("image")MultipartFile image) throws IOException {

        String imageUrl = s3Uploader.upload(image);

        dto.injectImageUrl(imageUrl);

        productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //상품 수정
    @PutMapping("/{id}")
    @Operation(summary = "상품 수정",description = "파라미터로 상품id, 상품 dto를 받고 상품 수정")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        productService.updateProduct(id, dto);
        return ResponseEntity.ok().build();
    }

    //인기순,조회수,구매수 상품 조회(기본값 인기수)
    @GetMapping("/sorted")
    @Operation(summary = "상품 인기수,조회수,구매수 정렬",description = "파라미터로 pageable(정렬조건) 받고 주문 조회")
    public ResponseEntity<Page<ProductResponseDto>> sorted(@ParameterObject ProductSearchCondition condition) {
        return ResponseEntity.ok(productService.getSortedProducts(condition));

    }
}
