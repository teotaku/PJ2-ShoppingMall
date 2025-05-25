package supercoding.pj2.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Setter
@Getter
@Schema(description = "상품 검색 조건")
public class ProductSearchCondition {
    @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
    private int page = 0;
    @Schema(description = "페이지 크기", example = "10")
    private int size = 10;
    @Schema(description = "정렬 기준", allowableValues = {"createdAt", "views", "purchases", "popularity"})
    private String sortBy = "createdAt";
    @Schema(description = "정렬 방향 (asc 또는 desc)", example = "desc")
    private String direction = "desc";
    @Schema(description = "검색 키워드", example = "")
    private String keyword;
    @Schema(description = "카테고리 ID", example = "2")
    private Long categoryId;


    public Pageable toPageable() {
        return PageRequest.of(this.page, this.size);
    }
}
