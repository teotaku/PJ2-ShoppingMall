package bymyself.pj2.service;

import bymyself.pj2.dto.response.CategoryDto;
import bymyself.pj2.entity.Category;
import bymyself.pj2.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    }
}
