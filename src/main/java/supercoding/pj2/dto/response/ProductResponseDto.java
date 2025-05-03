package bymyself.pj2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private int stock;




}
