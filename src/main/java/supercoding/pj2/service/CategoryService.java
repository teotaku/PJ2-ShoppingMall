package supercoding.pj2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercoding.pj2.dto.response.CategoryDto;
import supercoding.pj2.entity.Category;
import supercoding.pj2.exception.DuplicateCategoryException;
import supercoding.pj2.exception.NotFoundException;
import supercoding.pj2.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(Category::toDto)
                .toList();

    }

    public void create(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new DuplicateCategoryException();
        }
        Category category = Category.create(name);
        categoryRepository.save(category);

    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("카테고리를 찾을 수 없습니다."));
        categoryRepository.delete(category);

    }

    }
