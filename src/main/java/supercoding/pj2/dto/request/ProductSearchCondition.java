package supercoding.pj2.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Schema(description = "상품 검색 조건")
public class ProductSearchCondition {
    @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
    private int page = 0;
    @Schema(description = "페이지 크기", example = "10")
    private int size = 10;
    @Schema(description = "정렬 기준", example = "createdAt")
    private String sortBy = "createdAt";
    @Schema(description = "정렬 방향 (asc 또는 desc)", example = "desc")
    private String direction = "desc";
    @Schema(description = "검색 키워드", example = "키보드")
    private String keyword;
    @Schema(description = "카테고리 ID", example = "3")
    private Long categoryId;


    public Pageable toPageable() {
        return PageRequest.of(
                this.page,
                this.size,
                Sort.by(Sort.Direction.fromString(this.direction), this.sortBy)
        );
    }
}
