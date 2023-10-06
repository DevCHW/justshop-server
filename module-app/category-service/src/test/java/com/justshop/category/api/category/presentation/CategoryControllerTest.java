package com.justshop.category.api.category.presentation;

import com.justshop.category.RestDocsSupport;
import com.justshop.category.api.category.application.CategoryService;
import com.justshop.category.api.category.application.dto.response.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends RestDocsSupport {

    private CategoryService categoryService = Mockito.mock(CategoryService.class);

    @Override
    protected Object initController() {
        return new CategoryController(categoryService);
    }

    @DisplayName("카테고리 목록을 계층형 카테고리 형식으로 조회한다.")
    @Test
    void getCategories() throws Exception {
        // given
        List<CategoryResponse> response = generateCategoryResponses();

        given(categoryService.getAll()).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("category-get-all",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("카테고리 정보"),
                                        fieldWithPath("data[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                        fieldWithPath("data[].parentId").type(JsonFieldType.NUMBER).optional().description("상위 카테고리 ID"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("카테고리 이름"),
                                        fieldWithPath("data[].depth").type(JsonFieldType.NUMBER).description("깊이"),
                                        fieldWithPath("data[].children[]").type(JsonFieldType.ARRAY).description("하위 카테고리 정보"),
                                        fieldWithPath("data[].children[].categoryId").type(JsonFieldType.NUMBER).description("하위 카테고리 ID"),
                                        fieldWithPath("data[].children[].parentId").type(JsonFieldType.NUMBER).optional().description("하위 상위 카테고리 ID"),
                                        fieldWithPath("data[].children[].name").type(JsonFieldType.STRING).description("하위 카테고리 이름"),
                                        fieldWithPath("data[].children[].depth").type(JsonFieldType.NUMBER).description("하위 카테고리 깊이"),
                                        fieldWithPath("data[].children[].children[]").type(JsonFieldType.ARRAY).description("하위 카테고리 정보")
                                )
                        )
                );
    }

    private List<CategoryResponse> generateCategoryResponses() {
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