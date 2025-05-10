package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercoding.pj2.dto.response.CategoryDto;
import supercoding.pj2.entity.Category;
import supercoding.pj2.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;


    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(Category::toDto)
                .toList();

    }

    public void create(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        }
        Category category = Category.create(name);
        categoryRepository.save(category);

    }
}
