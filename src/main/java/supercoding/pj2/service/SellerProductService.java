package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercoding.pj2.dto.request.SellerProductRequestDto;
import supercoding.pj2.dto.response.SellerProductListResponseDto;
import supercoding.pj2.dto.response.SellerProductResponseDto;
import supercoding.pj2.entity.Product;
import supercoding.pj2.entity.SellerItem;
import supercoding.pj2.repository.ProductRepository;
import supercoding.pj2.repository.SellerItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerProductService {

    private final SellerItemRepository sellerItemRepository;
    private final ProductRepository productRepository;

    public SellerProductResponseDto registerProduct(SellerProductRequestDto request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        SellerItem sellerItem = SellerItem.builder()
                .userId(request.getUserId())
                .productId(product.getId())
                .stock(request.getStock())
                .build();

        sellerItemRepository.save(sellerItem);

        return SellerProductResponseDto.builder()
                .message("판매 물품이 등록되었습니다.")
                .sellerItemId(sellerItem.getSellerItemId())
                .build();
    }

    public List<SellerProductListResponseDto> getSellerProducts(Long userId) {
        List<SellerItem> items = sellerItemRepository.findByUserId(userId);

        return items.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음"));
            return new SellerProductListResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice().intValue(),  // BigDecimal → int
                    item.getStock()
            );
        }).collect(Collectors.toList());
    }

}
