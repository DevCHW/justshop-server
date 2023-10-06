package com.justshop.product.api.order.presentation;

import com.justshop.product.RestDocsSupport;
import com.justshop.product.api.order.application.OrderService;
import com.justshop.product.api.product.application.dto.response.OrderProductInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

class OrderControllerTest extends RestDocsSupport {

    private OrderService orderService = Mockito.mock(OrderService.class);

    @Override
    protected Object initController() {
        return new OrderController(orderService);
    }

    // 주문상품 조회
    @DisplayName("productOptionId 리스트를 받아 주문된 상품목록을 조회할 수 있다.")
    @Test
    void getOrderProductsInfo() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("productOptionId", "1");
        params.add("productOptionId", "2");
        params.add("productOptionId", "3");

        OrderProductInfo orderProductInfo1 = OrderProductInfo.builder()
                .productOptionId(1L)
                .productId(1L)
                .price(10000)
                .additionalPrice(0)
                .stockQuantity(100)
                .build();
        OrderProductInfo orderProductInfo2 = OrderProductInfo.builder()
                .productOptionId(2L)
                .productId(1L)
                .price(10000)
                .additionalPrice(0)
                .stockQuantity(100)
                .build();
        OrderProductInfo orderProductInfo3 = OrderProductInfo.builder()
                .productOptionId(3L)
                .productId(1L)
                .price(10000)
                .additionalPrice(0)
                .stockQuantity(100)
                .build();

        List<OrderProductInfo> response = List.of(orderProductInfo1, orderProductInfo2, orderProductInfo3);

        given(orderService.getOrderProductsInfo(any(List.class))).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/products/order-products")
                                .params(params)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("product-get-order-info",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("productOptionId").description("상품 옵션 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("주문 상품 정보"),
                                        fieldWithPath("data[].productOptionId").type(JsonFieldType.NUMBER).description("상품 옵션 ID"),
                                        fieldWithPath("data[].productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                        fieldWithPath("data[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                        fieldWithPath("data[].additionalPrice").type(JsonFieldType.NUMBER).description("옵션 추가금액"),
                                        fieldWithPath("data[].stockQuantity").type(JsonFieldType.NUMBER).description("재고수량")
                                )
                        )
                );
    }

}