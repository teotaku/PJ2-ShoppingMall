package supercoding.pj2.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin(origins = "*")
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
    @Operation(
            summary = "상품 등록 API",
            description = """
    multipart/form-data 형식으로 상품 정보를 등록합니다.  
    - `ProductRequestDto`는 `@ModelAttribute`로 파싱됩니다.  
    - 이미지 파일은 `image` 필드로 `MultipartFile`로 첨부해야 합니다.
    
     Swagger 사용 방법:
    - `image`: 파일 탭에서 이미지 선택  
    - 나머지 필드는 텍스트 입력  
    - `sizes[0].size`, `sizes[0].stock` 형태로 사이즈 배열 입력 가능
    """)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@ModelAttribute ProductRequestDto dto,
                                       @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        if (image != null) {
            String imageUrl = s3Uploader.upload(image);
            dto.injectImageUrl(imageUrl);
        }

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

    //상품삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "상품 삭제", description = "상품 id를 클라이언트가 전송하면 서버가 상품삭제")
    public ResponseEntity<Void> delte(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    //인기순,조회수,구매수 상품 조회(기본값 인기수)
    @GetMapping("/sorted")
    @Operation(
            summary = "상품 정렬 조회",
            description = """
    상품을 다양한 기준(createdAt, views, purchases, popularity)으로 정렬하여 조회합니다.
    
    - createdAt: 등록일 기준 최신순 정렬 (기본값)
    - views: 조회수 많은 순
    - purchases: 구매수 많은 순
    - popularity: 인기 점수(PopularityScore) 기준 정렬 (내림차순)
    
    페이징 및 정렬 방향(desc/asc)은 파라미터로 지정 가능합니다.
    """
    )
    public ResponseEntity<Page<ProductResponseDto>> sorted(@ParameterObject ProductSearchCondition condition) {

        return ResponseEntity.ok(productService.getSortedProducts(condition));

    }
}
