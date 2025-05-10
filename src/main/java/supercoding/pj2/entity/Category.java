package supercoding.pj2.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import supercoding.pj2.dto.response.CategoryDto;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;

    public CategoryDto toDto() {
        return new CategoryDto(id, name);
    }

    public static Category create(String name) {
        Category category = new Category();
        category.name = name;
        return category;
    }


}
