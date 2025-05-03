package bymyself.pj2.controller;


import bymyself.pj2.dto.request.ProductSearchCondition;
import bymyself.pj2.dto.response.ProductResponseDto;
import bymyself.pj2.entity.Product;
import bymyself.pj2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
