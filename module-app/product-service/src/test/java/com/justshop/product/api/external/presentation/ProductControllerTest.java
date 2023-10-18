package com.justshop.product.api.external.presentation;

import com.justshop.product.RestDocsSupport;
import com.justshop.product.api.external.application.ProductService;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.domain.entity.ProductCategory;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import com.justshop.product.domain.repository.querydsl.dto.SearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.justshop.product.api.external.application.dto.response.ProductResponse.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends RestDocsSupport {

    private final ProductService productService = Mockito.mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @DisplayName("상품 ID를 받아 상품 정보를 상세조회할 수 있다.")
    @Test
    void getProduct() throws Exception {
        // given
        Long productId = 1L;
        ProductResponse response = generateProductResponse(productId);

        given(productService.getProductInfo(anyLong())).willReturn(response);
        // when & then
        mockMvc.perform(
                        get("/api/v1/products/{productId}", productId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                // 기타 코드 생략. . .
                .andDo(document("product-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("productId").description("상품 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("상품 정보"),
                                        fieldWithPath("data.productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("상품명"),
                                        fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                        fieldWithPath("data.salesQuantity").type(JsonFieldType.NUMBER).description("상품 판매수량"),
                                        fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("상품 추천 갯수"),
                                        fieldWithPath("data.reviewCount").type(JsonFieldType.NUMBER).description("상품 후기 갯수"),
                                        fieldWithPath("data.status").type(JsonFieldType.STRING).description("상품 판매상태"),
                                        fieldWithPath("data.gender").type(JsonFieldType.STRING).description("남성용/여성용 구분"),
                                        fieldWithPath("data.detail").type(JsonFieldType.STRING).description("상품 상세 설명"),

                                        // 상품 옵션
                                        fieldWithPath("data.options[]").type(JsonFieldType.ARRAY).description("상품 옵션 정보 목록"),
                                        fieldWithPath("data.options[].productOptionId").type(JsonFieldType.NUMBER).description("상품 옵션 ID"),
                                        fieldWithPath("data.options[].size").type(JsonFieldType.STRING).description("사이즈"),
                                        fieldWithPath("data.options[].color").type(JsonFieldType.STRING).description("색상"),
                                        fieldWithPath("data.options[].etc").type(JsonFieldType.STRING).optional().description("기타옵션"),
                                        fieldWithPath("data.options[].additionalPrice").type(JsonFieldType.NUMBER).description("추가금액"),
                                        fieldWithPath("data.options[].stockQuantity").type(JsonFieldType.NUMBER).description("재고수량"),

                                        // 상품 이미지
                                        fieldWithPath("data.images[]").type(JsonFieldType.ARRAY).description("상품 이미지 목록"),
                                        fieldWithPath("data.images[].productImageId").type(JsonFieldType.NUMBER).description("상품 이미지 ID"),
                                        fieldWithPath("data.images[].basicYn").type(JsonFieldType.BOOLEAN).description("기본이미지 여부"),
                                        fieldWithPath("data.images[].saveFileName").type(JsonFieldType.STRING).description("저장된 파일명"),
                                        fieldWithPath("data.images[].originFileName").type(JsonFieldType.STRING).description("업로드 파일명"),
                                        fieldWithPath("data.images[].path").type(JsonFieldType.STRING).description("파일 경로")
                                )
                        )
                );
    }

    @DisplayName("검색 조건에 따라서 상품 목록을 페이징하여 반환한다.")
    @Test
    void getProductsForPage() throws Exception {
        // given
        String name = null;
        Integer minPrice = null;
        Integer maxPrice = null;
        SellingStatus status = null;
        Gender gender = null;

        List<ProductResponse> content = new ArrayList<>();
        for(long i=0; i<2; i++) {
            content.add(generateProductResponse(i));
        }

        Page<ProductResponse> response = new PageImpl<>(content, PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "createdDateTime")), 2);

        given(productService.getProductsForPage(any(SearchCondition.class), any(Pageable.class)))
                .willReturn(response);
        // when & then
        mockMvc.perform(
                        get("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", "0")
                                .param("size", "10")
                                .param("sort", "createdDateTime,desc")
                                .param("name", "Test")
                                .param("minPrice", "0")
                                .param("maxPrice", "10000000")
                                .param("status", "SELLING")
                                .param("gender", "FREE"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                // 기타 코드 생략. . .
                .andDo(document("products-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("page").description("페이지").optional(),
                                        parameterWithName("size").description("데이터 크기").optional(),
                                        parameterWithName("sort").description("정렬 방식").optional(),
                                        parameterWithName("name").description("상품명").optional(),
                                        parameterWithName("minPrice").description("최소가격").optional(),
                                        parameterWithName("maxPrice").description("최대가격").optional(),
                                        parameterWithName("status").description("상품 상태").optional(),
                                        parameterWithName("gender").description("남/녀 구분").optional()
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("조회 결과"),
                                        fieldWithPath("data.content[].productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                        fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("상품명"),
                                        fieldWithPath("data.content[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                        fieldWithPath("data.content[].salesQuantity").type(JsonFieldType.NUMBER).description("상품 판매수량"),
                                        fieldWithPath("data.content[].likeCount").type(JsonFieldType.NUMBER).description("상품 추천 갯수"),
                                        fieldWithPath("data.content[].reviewCount").type(JsonFieldType.NUMBER).description("상품 후기 갯수"),
                                        fieldWithPath("data.content[].status").type(JsonFieldType.STRING).description("상품 판매상태"),
                                        fieldWithPath("data.content[].gender").type(JsonFieldType.STRING).description("남성용/여성용 구분"),
                                        fieldWithPath("data.content[].detail").type(JsonFieldType.STRING).description("상품 상세 설명"),
                                        fieldWithPath("data.content[].likeExists").type(JsonFieldType.BOOLEAN).optional().description("상품 좋아요 여부"),

                                        // 상품 옵션
                                        fieldWithPath("data.content[].options[]").type(JsonFieldType.ARRAY).description("상품 옵션 정보 목록"),
                                        fieldWithPath("data.content[].options[].productOptionId").type(JsonFieldType.NUMBER).description("상품 옵션 ID"),
                                        fieldWithPath("data.content[].options[].size").type(JsonFieldType.STRING).description("사이즈"),
                                        fieldWithPath("data.content[].options[].color").type(JsonFieldType.STRING).description("색상"),
                                        fieldWithPath("data.content[].options[].etc").type(JsonFieldType.STRING).optional().description("기타옵션"),
                                        fieldWithPath("data.content[].options[].additionalPrice").type(JsonFieldType.NUMBER).description("추가금액"),
                                        fieldWithPath("data.content[].options[].stockQuantity").type(JsonFieldType.NUMBER).description("재고수량"),

                                        // 상품 이미지
                                        fieldWithPath("data.content[].images[]").type(JsonFieldType.ARRAY).description("상품 이미지 목록"),
                                        fieldWithPath("data.content[].images[].productImageId").type(JsonFieldType.NUMBER).description("상품 이미지 ID"),
                                        fieldWithPath("data.content[].images[].basicYn").type(JsonFieldType.BOOLEAN).description("기본이미지 여부"),
                                        fieldWithPath("data.content[].images[].saveFileName").type(JsonFieldType.STRING).description("저장된 파일명"),
                                        fieldWithPath("data.content[].images[].originFileName").type(JsonFieldType.STRING).description("업로드 파일명"),
                                        fieldWithPath("data.content[].images[].path").type(JsonFieldType.STRING).description("파일 경로"),
                                        
                                        // 페이지 정보
                                        fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                        fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                        fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                                        fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                        fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 여부"),
                                        fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("미정렬 여부"),
                                        fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("오프셋"),
                                        fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부"),
                                        fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 미적용 여부"),
                                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("총 요소 수"),
                                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                        fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                        fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                        fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                        fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                                        fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                        fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 여부"),
                                        fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("미정렬 여부"),
                                        fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 요소 수"),
                                        fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                        fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("빈 페이지 여부")
                                )
                        )
                );
    }

    @DisplayName("검색 조건에 따라서 카테고리 번호에 따른 상품 목록을 페이징하여 반환한다.")
    @Test
    void getProductsCategoryForPage() throws Exception {
        // given
        Long categoryId = 1L;
        String name = null;
        Integer minPrice = null;
        Integer maxPrice = null;
        SellingStatus status = null;
        Gender gender = null;

        List<ProductResponse> content = new ArrayList<>();
        for(long i=0; i<2; i++) {
            content.add(generateProductResponse(i));
        }

        Page<ProductResponse> response = new PageImpl<>(content, PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "createdDateTime")), 2);

        given(productService.getProductsCategoryForPage(anyLong(), any(SearchCondition.class), any(Pageable.class)))
                .willReturn(response);
        // when & then
        mockMvc.perform(
                        get("/api/v1/products/categories/{categoryId}", categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", "0")
                                .param("size", "10")
                                .param("sort", "createdDateTime,desc")
                                .param("name", "Test")
                                .param("minPrice", "0")
                                .param("maxPrice", "10000000")
                                .param("status", "SELLING")
                                .param("gender", "FREE"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                // 기타 코드 생략. . .
                .andDo(document("products-get-by-categoryId",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("page").description("페이지").optional(),
                                        parameterWithName("size").description("데이터 크기").optional(),
                                        parameterWithName("sort").description("정렬 방식").optional(),
                                        parameterWithName("name").description("상품명").optional(),
                                        parameterWithName("minPrice").description("최소가격").optional(),
                                        parameterWithName("maxPrice").description("최대가격").optional(),
                                        parameterWithName("status").description("상품 상태").optional(),
                                        parameterWithName("gender").description("남/녀 구분").optional()
                                ),
                                pathParameters(
                                        parameterWithName("categoryId").description("카테고리 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("조회 결과"),
                                        fieldWithPath("data.content[].productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                        fieldWithPath("data.content[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                        fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("상품명"),
                                        fieldWithPath("data.content[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                        fieldWithPath("data.content[].salesQuantity").type(JsonFieldType.NUMBER).description("상품 판매수량"),
                                        fieldWithPath("data.content[].likeCount").type(JsonFieldType.NUMBER).description("상품 추천 갯수"),
                                        fieldWithPath("data.content[].reviewCount").type(JsonFieldType.NUMBER).description("상품 후기 갯수"),
                                        fieldWithPath("data.content[].status").type(JsonFieldType.STRING).description("상품 판매상태"),
                                        fieldWithPath("data.content[].gender").type(JsonFieldType.STRING).description("남성용/여성용 구분"),
                                        fieldWithPath("data.content[].detail").type(JsonFieldType.STRING).description("상품 상세 설명"),
                                        fieldWithPath("data.content[].likeExists").type(JsonFieldType.BOOLEAN).optional().description("상품 좋아요 여부"),

                                        // 상품 옵션
                                        fieldWithPath("data.content[].options[]").type(JsonFieldType.ARRAY).description("상품 옵션 정보 목록"),
                                        fieldWithPath("data.content[].options[].productOptionId").type(JsonFieldType.NUMBER).description("상품 옵션 ID"),
                                        fieldWithPath("data.content[].options[].size").type(JsonFieldType.STRING).description("사이즈"),
                                        fieldWithPath("data.content[].options[].color").type(JsonFieldType.STRING).description("색상"),
                                        fieldWithPath("data.content[].options[].etc").type(JsonFieldType.STRING).optional().description("기타옵션"),
                                        fieldWithPath("data.content[].options[].additionalPrice").type(JsonFieldType.NUMBER).description("추가금액"),
                                        fieldWithPath("data.content[].options[].stockQuantity").type(JsonFieldType.NUMBER).description("재고수량"),

                                        // 상품 이미지
                                        fieldWithPath("data.content[].images[]").type(JsonFieldType.ARRAY).description("상품 이미지 목록"),
                                        fieldWithPath("data.content[].images[].productImageId").type(JsonFieldType.NUMBER).description("상품 이미지 ID"),
                                        fieldWithPath("data.content[].images[].basicYn").type(JsonFieldType.BOOLEAN).description("기본이미지 여부"),
                                        fieldWithPath("data.content[].images[].saveFileName").type(JsonFieldType.STRING).description("저장된 파일명"),
                                        fieldWithPath("data.content[].images[].originFileName").type(JsonFieldType.STRING).description("업로드 파일명"),
                                        fieldWithPath("data.content[].images[].path").type(JsonFieldType.STRING).description("파일 경로"),

                                        // 페이지 정보
                                        fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                        fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                        fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                                        fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                        fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 여부"),
                                        fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("미정렬 여부"),
                                        fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("오프셋"),
                                        fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부"),
                                        fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 미적용 여부"),
                                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("총 요소 수"),
                                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                        fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                        fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                        fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                        fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                                        fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                        fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 여부"),
                                        fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("미정렬 여부"),
                                        fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 요소 수"),
                                        fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                        fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("빈 페이지 여부")
                                )
                        )
                );
    }

    private ProductResponse generateProductResponse(Long productId) {
        ProductOptionResponse productOption1 = generateProductOptionResponse(1L, Size.L, Color.BLACK);
        ProductOptionResponse productOption2 = generateProductOptionResponse(2L, Size.M, Color.BLACK);
        ProductOptionResponse productOption3 = generateProductOptionResponse(3L, Size.S, Color.BLACK);
        ProductImageResponse productImage1 = generateProductImageResponse(1L, true, "TEST_PRODUCT_IMAGE1.PNG");
        ProductImageResponse productImage2 = generateProductImageResponse(2L, false, "TEST_PRODUCT_IMAGE2.PNG");
        ProductImageResponse productImage3 = generateProductImageResponse(3L, false, "TEST_PRODUCT_IMAGE3.PNG");

        ProductResponse response = builder()
                .productId(productId)
                .categoryId(1L)
                .name("Test Product Name")
                .price(30000)
                .salesQuantity(10000L)
                .likeCount(1000L)
                .reviewCount(1000L)
                .status(SellingStatus.SELLING)
                .gender(Gender.FREE)
                .detail("Test Product Description.")
                .options(List.of(productOption1, productOption2, productOption3))
                .images(List.of(productImage1, productImage2, productImage3))
                .build();
        return response;
    }

    private ProductImageResponse generateProductImageResponse(long productImageId, boolean basicYn, String originFileName) {
        return ProductImageResponse.builder()
                .productImageId(productImageId)
                .basicYn(basicYn)
                .saveFileName(UUID.randomUUID() + ".PNG")
                .originFileName(originFileName)
                .path("/home/etc/profile/")
                .build();
    }

    private ProductOptionResponse generateProductOptionResponse(long productOptionId, Size size, Color color) {
        return ProductOptionResponse.builder()
                .productOptionId(productOptionId)
                .size(size)
                .color(color)
                .etc(null)
                .additionalPrice(0L)
                .stockQuantity(1000L)
                .build();
    }

}