package supercoding.pj2.dto.request;


import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class ProductSearchCondition {

    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String direction = "desc";
    private String keyword;
    private Long categoryId;


    public Pageable toPageable() {
        return PageRequest.of(
                this.page,
                this.size,
                Sort.by(Sort.Direction.fromString(this.direction), this.sortBy)
        );
    }
}
