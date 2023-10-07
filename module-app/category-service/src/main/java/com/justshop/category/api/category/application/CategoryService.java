package com.justshop.category.api.category.application;

import com.justshop.category.api.category.application.dto.response.CategoryResponse;
import com.justshop.category.domain.entity.Category;
import com.justshop.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /* 카테고리 목록 조회 */
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAllByOrderByDepthAsc();
        // 계층형으로 변환
        List<CategoryResponse> categoryResponses = convertHierarchy(categories);
        return categoryResponses;
    }

    private List<CategoryResponse> convertHierarchy(List<Category> categories) {
        Map<Long, CategoryResponse> map = new ConcurrentHashMap<>();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        categories.forEach(category -> {
                    CategoryResponse categoryResponse = CategoryResponse.from(category);

                    if (category.getParent() != null) {
                        CategoryResponse parent = map.get(category.getParent().getId());
                        categoryResponse.setParentId(parent.getCategoryId());
                        parent.getChildren().add(categoryResponse);
                    }

                    if (category.getParent() == null) {
                        categoryResponses.add(categoryResponse);
                    }
                    map.put(category.getId(), categoryResponse);
                });
        return categoryResponses;
    }

    /* 카테고리 ID 목록을 하위 카테고리 ID와 함께 조회한다. */
    public List<Long> getChildrenIds(Long categoryId) {
        return categoryRepository.findAllByParentIdOrId(categoryId, categoryId).stream()
                .map(Category::getId).collect(Collectors.toList());
    }

}
