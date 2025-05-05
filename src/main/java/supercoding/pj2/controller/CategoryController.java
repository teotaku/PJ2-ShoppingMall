package supercoding.pj2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.response.CategoryDto;
import supercoding.pj2.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping //카테고리 전체 조회
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping//카테고리 등록
    public ResponseEntity<Void> create(@RequestParam String name) {
        categoryService.create(name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
