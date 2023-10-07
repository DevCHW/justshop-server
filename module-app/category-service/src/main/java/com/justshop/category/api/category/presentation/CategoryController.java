package com.justshop.category.api.category.presentation;

import com.justshop.category.api.category.application.CategoryService;
import com.justshop.category.api.category.application.dto.response.CategoryResponse;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /* 카테고리 목록 조회 */
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> response = categoryService.getAll();
        return ApiResponse.ok(response);
    }

    /* 카테고리 번호 목록 조회, 상위 카테고리 번호 입력 시 해당하는 자식들의 카테고리 번호까지 조회한다. */
    @GetMapping("/{categoryId}/children-ids")
    public ApiResponse<List<Long>> getChildrenIds(@PathVariable Long categoryId) {
        List<Long> response = categoryService.getChildrenIds(categoryId);
        return ApiResponse.ok(response);
    }

}
