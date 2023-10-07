package com.justshop.category.api.category.presentation;

import com.justshop.category.DataFactoryUtil;
import com.justshop.category.RestDocsSupport;
import com.justshop.category.api.category.application.CategoryService;
import com.justshop.category.api.category.application.dto.response.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
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
        List<CategoryResponse> response = DataFactoryUtil.generateCategoryResponses();

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

    @DisplayName("카테고리 ID를 받아서 하위 카테고리의 ID 목록을 조회한다.")
    @Test
    void getChildrenIds() throws Exception {
        // given
        List<Long> response = List.of(1L, 2L, 3L, 4L, 5L);

        given(categoryService.getChildrenIds(anyLong())).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/categories/3/children-ids")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("category-get-children-ids",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("카테고리 ID 목록")
                                )
                        )
                );
    }

}