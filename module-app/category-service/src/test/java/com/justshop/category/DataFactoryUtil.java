package com.justshop.category;

import com.justshop.category.api.category.application.dto.response.CategoryResponse;
import com.justshop.category.domain.entity.Category;

import java.util.List;

public class DataFactoryUtil {

    private DataFactoryUtil() {
    }

    /* 계층형 카테고리 형식 만들기 */
    public static List<Category> generateCategories() {
        Category category1 = Category.builder()
                .name("상의")
                .parent(null)
                .depth(0)
                .build();
        Category category2 = Category.builder()
                .name("하의")
                .parent(null)
                .depth(0)
                .build();

        Category category1_1 = Category.builder()
                .name("니트/스웨터")
                .parent(category1)
                .depth(1)
                .build();
        Category category1_2 = Category.builder()
                .name("후드티셔츠")
                .parent(category1)
                .depth(1)
                .build();
        Category category1_3 = Category.builder()
                .name("긴소매 티셔츠")
                .parent(category1)
                .depth(1)
                .build();
        Category category1_4 = Category.builder()
                .name("기타 상의")
                .parent(category1)
                .depth(1)
                .build();

        category1.addChild(category1_1);
        category1.addChild(category1_2);
        category1.addChild(category1_3);
        category1.addChild(category1_4);

        Category category2_1 = Category.builder()
                .name("코튼 팬츠")
                .parent(category2)
                .depth(1)
                .build();
        Category category2_2 = Category.builder()
                .name("데님 팬츠")
                .parent(category2)
                .depth(1)
                .build();
        Category category2_3 = Category.builder()
                .name("숏 팬츠")
                .parent(category2)
                .depth(1)
                .build();
        Category category2_4 = Category.builder()
                .name("기타 하의")
                .parent(category2)
                .depth(1)
                .build();
        category2.addChild(category2_1);
        category2.addChild(category2_2);
        category2.addChild(category2_3);
        category2.addChild(category2_4);
        return List.of(category1, category2);
    }

    public static List<CategoryResponse> generateCategoryResponses() {
        CategoryResponse response1 = CategoryResponse.builder()
                .categoryId(1L)
                .name("상의")
                .parentId(null)
                .depth(0)
                .build();

        CategoryResponse response1_1 = CategoryResponse.builder()
                .categoryId(3L)
                .name("니트/스웨터")
                .parentId(1L)
                .depth(1)
                .build();
        CategoryResponse response1_2 = CategoryResponse.builder()
                .categoryId(4L)
                .name("후드티셔츠")
                .parentId(1L)
                .depth(1)
                .build();
        CategoryResponse response1_3 = CategoryResponse.builder()
                .categoryId(5L)
                .name("긴소매 티셔츠")
                .parentId(1L)
                .depth(1)
                .build();
        CategoryResponse response1_4 = CategoryResponse.builder()
                .categoryId(6L)
                .name("기타 상의")
                .parentId(1L)
                .depth(1)
                .build();
        response1.getChildren().add(response1_1);
        response1.getChildren().add(response1_2);
        response1.getChildren().add(response1_3);
        response1.getChildren().add(response1_4);

        CategoryResponse response2 = CategoryResponse.builder()
                .categoryId(2L)
                .name("하의")
                .parentId(null)
                .depth(0)
                .build();

        CategoryResponse response2_1 = CategoryResponse.builder()
                .categoryId(7L)
                .name("코튼 팬츠")
                .parentId(2L)
                .depth(1)
                .build();
        CategoryResponse response2_2 = CategoryResponse.builder()
                .categoryId(8L)
                .name("데님 팬츠")
                .parentId(2L)
                .depth(1)
                .build();
        CategoryResponse response2_3 = CategoryResponse.builder()
                .categoryId(9L)
                .name("숏 팬츠")
                .parentId(2L)
                .depth(1)
                .build();
        CategoryResponse response2_4 = CategoryResponse.builder()
                .categoryId(10L)
                .name("기타 하의")
                .parentId(2L)
                .depth(1)
                .build();
        response1.getChildren().add(response2_1);
        response1.getChildren().add(response2_2);
        response1.getChildren().add(response2_3);
        response1.getChildren().add(response2_4);

        return List.of(response1, response2);
    }
}
