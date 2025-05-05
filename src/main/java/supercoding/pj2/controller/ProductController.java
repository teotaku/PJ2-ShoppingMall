package supercoding.pj2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.request.ProductRequestDto;
import supercoding.pj2.dto.request.ProductSearchCondition;
import supercoding.pj2.dto.response.ProductResponseDto;
import supercoding.pj2.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //전체상품조회
    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAll(ProductSearchCondition condition) {
        return ResponseEntity.ok(productService.getAllProducts(condition));
    }


    //카테고리 조회
    @GetMapping("/category")
    public ResponseEntity<Page<ProductResponseDto>> getByCategoryPaged(ProductSearchCondition condition) {
        return ResponseEntity.ok(productService.getByCategory(condition));
    }

    //키워드로 조회
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDto>> getByKeyword(ProductSearchCondition condition) {
        return ResponseEntity.ok(productService.getByCategory(condition));
    }

    //아이디로 단건 조회
    @GetMapping("/id")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    //상품 등록
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductRequestDto dto) {
        productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        productService.updateProduct(id, dto);
        return ResponseEntity.ok().build();
    }

    //인기순,조회수,구매수 상품 조회(기본값 인기수)
    @GetMapping("/sorted")
    public ResponseEntity<Page<ProductResponseDto>> sorted(ProductSearchCondition condition) {
        return ResponseEntity.ok(productService.getSortedProducts(condition));

    }
}
