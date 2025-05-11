package supercoding.pj2.service;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supercoding.pj2.dto.request.ProductRequestDto;
import supercoding.pj2.dto.request.ProductSearchCondition;
import supercoding.pj2.dto.response.ProductResponseDto;
import supercoding.pj2.entity.Product;
import supercoding.pj2.exception.InvalidSearchConditionException;
import supercoding.pj2.exception.NotFoundException;
import supercoding.pj2.repository.CategoryRepository;
import supercoding.pj2.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;


    //전체상품목록 조회(페이징 포함) 클라이언트에서 받은 검색조건 포함하여 상품 목록을 page형태로 반환.
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getAllProducts(ProductSearchCondition condition) {
        return productRepository.findAll(condition.toPageable())
                .map(Product::toDto);

    }
    //키워드로 검색 페이징처리
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findByKeyword(ProductSearchCondition condition) {
        String keyword = condition.getKeyword();
        if (keyword == null || keyword.isBlank()) {
            throw new InvalidSearchConditionException();
        }

        Page<Product> result = productRepository
                .findByNameContaining(condition.getKeyword(),condition.toPageable());

        if (result.isEmpty()) {
            throw new NotFoundException("검색 결과가 없습니다.");
        }
        return result.map(Product::toDto);
    }

    //특정상품 단건조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
        return product.toDto();
    }

    //카테고리별 상품조회
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getByCategory(ProductSearchCondition condition) {

        Page<Product> result = productRepository
                .findByCategoryId(condition.getCategoryId(), condition.toPageable());
        if (result.isEmpty()) {
            throw new NotFoundException("해당 카테고리 상품이 없습니다.");
        }
        return result.map(Product::toDto);
    }
    //상품 등록
    public void createProduct(ProductRequestDto dto) {
        Product product = dto.toEntity();
        productRepository.save(product);
    }

    //상품 수정
    public void updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("상품이 존재하지 않습니다."));
        product.update(dto);
    }

    //인기순,조회수,구매순 정렬 메서드 구분없이 한 메서드에서 처리
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getSortedProducts(ProductSearchCondition condition) {
        Pageable pageable = condition.toPageable();

        switch (condition.getSortBy()) {
            case "views" :
                return productRepository.findAllByOrderByViewCountDesc(pageable)
                        .map(Product::toDto);
            case "purchases" :
                return productRepository.findAllByOrderByPurchaseCountDesc(pageable)
                        .map(Product::toDto);

            case "popularity":
            default:
                Page<Product> page = productRepository.findAll(pageable);

                List<Product> sorted = page.getContent().stream()
                        .sorted(Comparator.comparing(Product::getPopularityScore).reversed())
                        .toList();

                return new PageImpl<>(sorted, pageable, page.getTotalElements())
                        .map(Product::toDto);
        }

    }



}
