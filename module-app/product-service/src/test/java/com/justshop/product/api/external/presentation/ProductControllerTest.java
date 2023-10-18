package com.justshop.product.api.external.presentation;

import com.justshop.product.RestDocsSupport;
import com.justshop.product.api.external.application.ProductService;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.UUID;

import static com.justshop.product.api.external.application.dto.response.ProductResponse.*;
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
        ProductOptionResponse productOption1 = generateProductOption(1L, Size.L, Color.BLACK);
        ProductOptionResponse productOption2 = generateProductOption(2L, Size.M, Color.BLACK);
        ProductOptionResponse productOption3 = generateProductOption(3L, Size.S, Color.BLACK);
        ProductImageResponse productImage1 = generateProductImage(1L, true, "TEST_PRODUCT_IMAGE1.PNG");
        ProductImageResponse productImage2 = generateProductImage(2L, false, "TEST_PRODUCT_IMAGE2.PNG");
        ProductImageResponse productImage3 = generateProductImage(3L, false, "TEST_PRODUCT_IMAGE3.PNG");
        ProductResponse response = builder()
                .productId(productId)
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
                                        fieldWithPath("data.images[].fileId").type(JsonFieldType.NUMBER).description("파일 ID"),
                                        fieldWithPath("data.images[].saveFileName").type(JsonFieldType.STRING).description("저장된 파일명"),
                                        fieldWithPath("data.images[].originFileName").type(JsonFieldType.STRING).description("원래 파일명"),
                                        fieldWithPath("data.images[].path").type(JsonFieldType.STRING).description("파일 경로")
                                )
                        )
                );
    }

    @DisplayName("상품 목록을 페이징하여 반환한다.")
    @Test
    void getProductsForPage() throws Exception {
        // given
        Long productId = 1L;
        ProductOptionResponse productOption1 = generateProductOption(1L, Size.L, Color.BLACK);
        ProductOptionResponse productOption2 = generateProductOption(2L, Size.M, Color.BLACK);
        ProductOptionResponse productOption3 = generateProductOption(3L, Size.S, Color.BLACK);
        ProductImageResponse productImage1 = generateProductImage(1L, true, "TEST_PRODUCT_IMAGE1.PNG");
        ProductImageResponse productImage2 = generateProductImage(2L, false, "TEST_PRODUCT_IMAGE2.PNG");
        ProductImageResponse productImage3 = generateProductImage(3L, false, "TEST_PRODUCT_IMAGE3.PNG");
        ProductResponse response = builder()
                .productId(productId)
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
                                        fieldWithPath("data.images[].fileId").type(JsonFieldType.NUMBER).description("파일 ID"),
                                        fieldWithPath("data.images[].saveFileName").type(JsonFieldType.STRING).description("저장된 파일명"),
                                        fieldWithPath("data.images[].originFileName").type(JsonFieldType.STRING).description("원래 파일명"),
                                        fieldWithPath("data.images[].path").type(JsonFieldType.STRING).description("파일 경로")
                                )
                        )
                );
    }

    private ProductImageResponse generateProductImage(long productImageId, boolean basicYn, String originFileName) {
        return ProductImageResponse.builder()
                .productImageId(productImageId)
                .basicYn(basicYn)
                .saveFileName(UUID.randomUUID() + ".PNG")
                .originFileName(originFileName)
                .path("/home/etc/profile/")
                .build();
    }

    private ProductOptionResponse generateProductOption(long productOptionId, Size size, Color color) {
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