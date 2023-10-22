package com.justshop.coupon.api.internal.presentation;

import com.justshop.coupon.RestDocsSupport;
import com.justshop.coupon.api.internal.application.InternalCouponService;
import com.justshop.coupon.api.internal.application.dto.CouponResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InternalCouponControllerTest extends RestDocsSupport {

    private InternalCouponService internalCouponService = Mockito.mock(InternalCouponService.class);
    @Override
    protected Object initController() {
        return new InternalCouponController(internalCouponService);
    }

    @DisplayName("쿠폰 ID를 받아 쿠폰 정보를 조회한다.")
    @Test
    void getMemberInfo() throws Exception {
        // given
        Long couponId = 1L;

        CouponResponse response = CouponResponse.builder()
                .id(couponId)
                .memberId(1L)
                .discountRate(20)
                .build();

        given(internalCouponService.getCoupon(anyLong()))
                .willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/internal/coupons/{couponId}", couponId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.memberId").value(response.getMemberId()))
                .andExpect(jsonPath("$.discountRate").value(response.getDiscountRate()))
                .andDo(document("coupon-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("couponId").description("쿠폰 ID")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("쿠폰 ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("discountRate").type(JsonFieldType.NUMBER).description("할인률")
                                )
                        )
                );
    }

}