package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supercoding.pj2.dto.request.ProductRequestDto;
import supercoding.pj2.dto.request.ProductRequestSizeDto;
import supercoding.pj2.dto.request.ProductSearchCondition;
import supercoding.pj2.dto.response.ProductResponseDto;
import supercoding.pj2.entity.Product;
import supercoding.pj2.entity.ProductSize;
import supercoding.pj2.exception.InvalidSearchConditionException;
import supercoding.pj2.exception.NotFoundException;
import supercoding.pj2.repository.CategoryRepository;
import supercoding.pj2.repository.ProductRepository;
import supercoding.pj2.repository.ProductSizeRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSizeRepository productSizeRepository;


    //전체상품목록 조회(페이징 포함) 클라이언트에서 받은 검색조건 포함하여 상품 목록을 page형태로 반환.
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getAllProducts(ProductSearchCondition condition) {
        return productRepository.findAll(condition.toPageable())
                .map(product -> {
                    List<ProductSize> sizes = productSizeRepository.findByProductId(product.getId());
                    return product.toDto(sizes);
                });
    }

    //키워드로 검색 페이징처리
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findByKeyword(ProductSearchCondition condition) {
        String keyword = condition.getKeyword();
        if (keyword == null || keyword.isBlank()) {
            throw new InvalidSearchConditionException();
        }

        Page<Product> result = productRepository
                .findByNameContaining(condition.getKeyword(), condition.toPageable());

        if (result.isEmpty()) {
            throw new NotFoundException("검색 결과가 없습니다.");
        }
        return productRepository.findAll(condition.toPageable())
                .map(product -> {
                    List<ProductSize> sizes = productSizeRepository.findByProductId(product.getId());
                    return product.toDto(sizes);
                });
    }

    //특정상품 단건조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

        List<ProductSize> sizes = productSizeRepository.findByProductId(product.getId());

        return product.toDto(sizes);
    }

    //카테고리별 상품조회
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getByCategory(ProductSearchCondition condition) {

        Page<Product> result = productRepository
                .findByCategoryId(condition.getCategoryId(), condition.toPageable());
        if (result.isEmpty()) {
            throw new NotFoundException("해당 카테고리 상품이 없습니다.");
        }
        return productRepository.findAll(condition.toPageable())
                .map(product -> {
                    List<ProductSize> sizes = productSizeRepository.findByProductId(product.getId());
                    return product.toDto(sizes);
                });
    }

    //상품 등록
    public void createProduct(ProductRequestDto dto) {
        Product product = dto.toEntity();
        productRepository.save(product);

        // 2. 사이즈 정보가 있을 경우만 처리
        // 3. 사이즈 리스트 처리
        List<ProductSize> sizes = Optional.ofNullable(dto.getSizes()) // null 방지
                .orElse(Collections.emptyList()) // null이면 빈 리스트 처리
                .stream()
                .filter(Objects::nonNull) // 요소가 null인 경우 제거
                .filter(sizeDto -> sizeDto.getSize() != null && sizeDto.getStock() >= 0) // 사이즈와 재고 유효성 체크
                .map(sizeDto -> ProductSize.builder()
                        .productId(product.getId()) // 위에서 저장된 product의 ID 주입
                        .size(sizeDto.getSize())    // 사이즈 정보 세팅
                        .stock(sizeDto.getStock())  // 재고 정보 세팅
                        .build())
                .toList();

        // 4. 유효한 사이즈가 존재할 경우에만 저장
        if (!sizes.isEmpty()) {
            productSizeRepository.saveAll(sizes);
        }
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
            case "views":
                return productRepository.findAllByOrderByViewCountDesc(pageable)
                        .map(product -> {
                            List<ProductSize> sizes = productSizeRepository.findByProductId(product.getId());
                            return product.toDto(sizes == null ? List.of() : sizes);
                        });

            case "purchases":
                return productRepository.findAllByOrderByPurchaseCountDesc(pageable)
                        .map(product -> {
                            List<ProductSize> sizes = productSizeRepository.findByProductId(product.getId());
                            return product.toDto(sizes == null ? List.of() : sizes);
                        });

            case "createdAt":
                return productRepository.findAll(
                        PageRequest.of(
                                condition.getPage(),
                                condition.getSize(),
                                Sort.by(Sort.Direction.fromString(condition.getDirection().toUpperCase().trim()), "createdAt")
                        )
                ).map(product -> {
                    List<ProductSize> sizes = productSizeRepository.findByProductId(product.getId());
                    return product.toDto(sizes == null ? List.of() : sizes);
                });

            case "popularity": {
                Page<Product> page = productRepository.findAll(pageable);
                List<Product> sorted = page.getContent().stream()
                        .sorted(Comparator.comparing(Product::getPopularityScore).reversed())
                        .toList();

                return new PageImpl<>(sorted, pageable, page.getTotalElements())
                        .map(product -> {
                            List<ProductSize> sizes = productSizeRepository.findByProductId(product.getId());
                            return product.toDto(sizes == null ? List.of() : sizes);
                        });
            }

            default:
                throw new IllegalArgumentException("지원하지 않는 정렬 조건: " + condition.getSortBy());
        }
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("상품 아이디를 찾을 수 없습니다."));
        productRepository.delete(product);

    }
}