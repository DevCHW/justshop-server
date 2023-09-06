package com.justshop.apiserver.external.member.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.justshop.apiserver.api.user.member.controller.MemberController;
import com.justshop.apiserver.api.user.member.service.MemberService;
import com.justshop.apiserver.api.user.member.service.response.MemberResponse;
import com.justshop.apiserver.api.response.ApiResponse;
import com.justshop.core.member.domain.enums.Gender;
import com.justshop.core.member.domain.enums.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends RestDocsSupport {

    private final MemberService memberService = Mockito.mock(MemberService.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }

    @DisplayName("회원 단건 조회 API")
    @Test
    void getOneMember() throws Exception {
        // given
        Long id = 1L;
        MemberResponse memberResponse = createMemberResponse();
        ApiResponse<MemberResponse> target = ApiResponse.ok(memberResponse);

        // stubbing
        given(memberService.getOneMember(any(Long.class)))
                .willReturn(memberResponse);

        String response = mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/members/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("members-get",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), // JSON 이쁘게 출력
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()), // JSON 이쁘게 출력
                                RequestDocumentation.pathParameters(RequestDocumentation.parameterWithName("id").description("회원 ID")),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.STRING)
                                                .description("코드"),
                                        PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메세지"),
                                        PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                .description("응답 데이터"),
                                        PayloadDocumentation.fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                                .description("회원 ID"),
                                        PayloadDocumentation.fieldWithPath("data.name").type(JsonFieldType.STRING)
                                                .description("회원 이름"),
                                        PayloadDocumentation.fieldWithPath("data.email").type(JsonFieldType.STRING)
                                                .description("회원 이메일"),
                                        PayloadDocumentation.fieldWithPath("data.gender").type(JsonFieldType.STRING)
                                                .description("회원 성별"),
                                        PayloadDocumentation.fieldWithPath("data.birthday").type(JsonFieldType.STRING)
                                                .description("회원 생년월일"),
                                        PayloadDocumentation.fieldWithPath("data.status").type(JsonFieldType.STRING)
                                                .description("회원 상태"),
                                        PayloadDocumentation.fieldWithPath("data.allowToMarketingNotification").type(JsonFieldType.BOOLEAN)
                                                .description("회원 마케팅수신동의여부")
                                )
                        )
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);


        ApiResponse<MemberResponse> result = objectMapper.readValue(response, new TypeReference<ApiResponse<MemberResponse>>() {
        });

        Assertions.assertThat(target).isEqualTo(result);
    }

    private MemberResponse createMemberResponse() {
        return MemberResponse.builder()
                .id(1L)
                .name("최현우")
                .email("ggoma003@naver.com")
                .gender(Gender.MAN)
                .birthday(LocalDate.of(1997, 1, 3))
                .status(MemberStatus.ACTIVE)
                .allowToMarketingNotification(true)
                .build();
    }

}