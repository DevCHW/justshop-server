package com.justshop.category.domain.repository;

import com.justshop.category.domain.entity.Category;
import com.justshop.jpa.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(JpaConfig.class)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("findAllOrderByDepthAsc 호출 시 depth 필드가 오름차순으로 정렬되어 조회되어야 한다.")
    @Test
    void findAllByOrderByDepthAsc() {
        // given
        List<Category> categories = generateCategories();
        categoryRepository.saveAll(categories);

        // when
        List<Category> result = categoryRepository.findAllByOrderByDepthAsc();

        // then
        assertThat(result).hasSize(10)
                .extracting("depth")
                .containsExactly(0, 0, 1, 1, 1, 1, 1, 1, 1, 1);
    }

    @DisplayName("카테고리 ID를 받아 하위 계층에 해당하는 카테고리목록을 함께 조회한다.")
    @Test
    void findAllByParentIdOrId() {
        // given
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

        List<Category> categories = List.of(category1, category2);
        List<Category> savedCategories = categoryRepository.saveAll(categories);

        Long parentId = savedCategories.stream()
                .filter(category -> category.getName().equals("상의"))
                .findFirst().map(Category::getId).orElse(null);

        // target을 조회하면 target에 해당하는 카테고리가 전부 나와야 함.
        List<Category> children = new ArrayList<>();
        categories.forEach(category -> {
            if (category.getId() == parentId) {
                children.add(category);
            }
        });

        // when
        List<Category> result = categoryRepository.findAllByParentIdOrId(parentId, parentId);

        // then
        assertThat(result).hasSize(5)
                .extracting("name", "depth")
                .containsExactlyInAnyOrder(
                        tuple("상의", 0),
                        tuple("니트/스웨터", 1),
                        tuple("후드티셔츠", 1),
                        tuple("긴소매 티셔츠", 1),
                        tuple("기타 상의", 1)
                );
    }

    private List<Category> generateCategories() {
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

}