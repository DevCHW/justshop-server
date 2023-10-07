package com.justshop.category.api.external.application;

import com.justshop.category.DataFactoryUtil;
import com.justshop.category.api.external.application.CategoryService;
import com.justshop.category.api.external.application.dto.response.CategoryResponse;
import com.justshop.category.domain.entity.Category;
import com.justshop.category.domain.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 목록을 계층 형식으로 조회한다.")
    @Test
    void getAll() {
        // given
        List<Category> savedCategories = categoryRepository.saveAll(DataFactoryUtil.generateCategories());

        // when
        List<CategoryResponse> result = categoryService.getAll();

        // then
        assertThat(result.size()).isEqualTo(savedCategories.size());
    }

}