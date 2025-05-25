package supercoding.pj2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import supercoding.pj2.dto.request.ProductRequestSizeDto;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    public String imageUrl;
    private String color;
    private int stock;
    private int viewCount;
    private int purchaseCount;
    private int popularityScore;
    private List<ProductResponseSizeDto> sizes;



}
