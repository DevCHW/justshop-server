package com.justshop.order.api.external.presentation;

import com.justshop.order.RestDocsSupport;
import com.justshop.order.api.external.application.OrderService;
import com.justshop.order.api.external.presentation.dto.request.CreateOrderRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends RestDocsSupport {

    private final OrderService orderService = Mockito.mock(OrderService.class);

    @Override
    protected Object initController() {
        return new OrderController(orderService);
    }

    @DisplayName("신규 주문을 등록한다.")
    @Test
    void orderSuccess() throws Exception {
        List<CreateOrderRequest.OrderProductRequest> orderProducts = List.of(
                new CreateOrderRequest.OrderProductRequest(1L, 1L, 10),
                new CreateOrderRequest.OrderProductRequest(1L, 3L, 20),
                new CreateOrderRequest.OrderProductRequest(1L, 5L, 30)
        );

        CreateOrderRequest request = CreateOrderRequest.builder()
                .memberId(1L)
                .orderProducts(orderProducts)
                .usePoint(1000L)
                .couponId(1L)
                .build();

        // when & then
        mockMvc.perform(
                        post("/api/v1/orders")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andDo(document("order-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("주문자 ID"),
                                fieldWithPath("couponId").type(JsonFieldType.NUMBER).description("사용 쿠폰 ID"),
                                fieldWithPath("usePoint").type(JsonFieldType.NUMBER).description("사용 포인트"),
                                fieldWithPath("orderProducts[]").type(JsonFieldType.ARRAY).description("주문상품 정보"),
                                fieldWithPath("orderProducts[].productOptionId").type(JsonFieldType.NUMBER).description("상품 옵션 ID"),
                                fieldWithPath("orderProducts[].productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                fieldWithPath("orderProducts[].quantity").type(JsonFieldType.NUMBER).description("주문 수량")

                        ),
                        responseFields(

                                fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"))
                        )
                );
    }

}