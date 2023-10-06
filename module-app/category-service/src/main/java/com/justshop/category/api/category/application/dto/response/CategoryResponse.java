package com.justshop.category.api.category.application.dto.response;

import com.justshop.category.domain.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CategoryResponse {

    private Long categoryId; // 카테고리 ID
    private String name; // 카테고리명
    private Integer depth; // 깊이
    private Long parentId; //부모 카테고리 ID
    private List<CategoryResponse> children = new ArrayList<>(); // 자식 카테고리 List

    @Builder
    public CategoryResponse(Long categoryId, String name, Long parentId, Integer depth) {
        this.categoryId = categoryId;
        this.name = name;
        this.depth = depth;
    }

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .parentId(category.getParent() == null ? null : category.getParent().getId())
                .depth(category.getDepth())
                .build();
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
