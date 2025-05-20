package supercoding.pj2.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercoding.pj2.dto.response.CategoryDto;
import supercoding.pj2.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping
    @Operation(summary = "전체 카테고리 조회", description = "저장된 모든 카테고리를 조회합니다.")//카테고리 전체 조회
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping
    @Operation(summary = "카테고리 등록", description = "새로운 카테고리를 등록합니다. 카테고리 이름을 입력받습니다.")//카테고리 등록
    public ResponseEntity<Void> create(@RequestParam String name) {
        categoryService.create(name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "카테고리 삭제", description = "아이디로 카테고리 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
