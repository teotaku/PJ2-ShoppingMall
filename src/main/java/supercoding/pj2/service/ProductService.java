package bymyself.pj2.service;

import bymyself.pj2.dto.request.ProductSearchCondition;
import bymyself.pj2.dto.response.ProductResponseDto;
import bymyself.pj2.entity.Category;
import bymyself.pj2.entity.Product;
import bymyself.pj2.repository.CategoryRepository;
import bymyself.pj2.repository.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    //전체상품목록 조회(페이징 포함) 클라이언트에서 받은 검색조건 포함하여 상품 목록을 page형태로 반환.
    public Page<ProductResponseDto> getAllProducts(ProductSearchCondition condition) {
        return productRepository.findAll(condition.toPageable())
                .map(Product::toDto);

    }

    //키워드로 검색 페이징처리
    public Page<ProductResponseDto> findByKeyword(ProductSearchCondition condition) {
        Page<Product> result = productRepository
                .findByNameContaining(condition.getKeyword(),condition.toPageable());

        if (result.isEmpty()) {
            throw new IllegalArgumentException("검색 결과가 없습니다.");
        }
        return result.map(Product::toDto);
    }


    //특정상품 단건조회
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수가 없습니다."));
        return product.toDto();
    }

    //카테고리별 상품조회
    public Page<ProductResponseDto> getByCategory(ProductSearchCondition condition) {


        Page<Product> result = productRepository
                .findByCategoryId(condition.getCategoryId(), condition.toPageable());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리 상품이 없습니다.");
        }
        return result.map(Product::toDto);

    }

}
