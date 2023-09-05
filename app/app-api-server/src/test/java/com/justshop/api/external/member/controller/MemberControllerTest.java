package com.justshop.api.external.member.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.justshop.api.external.member.service.MemberService;
import com.justshop.api.external.member.service.response.MemberResponse;
import com.justshop.api.response.ApiResponse;
import com.justshop.domain.member.domain.enumerated.Gender;
import com.justshop.domain.member.domain.enumerated.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
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
                        get("/api/v1/members/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("members-get",
                                preprocessRequest(prettyPrint()), // JSON 이쁘게 출력
                                preprocessResponse(prettyPrint()), // JSON 이쁘게 출력
                                pathParameters(parameterWithName("id").description("회원 ID")),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                .description("응답 데이터"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                                .description("회원 ID"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING)
                                                .description("회원 이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING)
                                                .description("회원 이메일"),
                                        fieldWithPath("data.gender").type(JsonFieldType.STRING)
                                                .description("회원 성별"),
                                        fieldWithPath("data.birthday").type(JsonFieldType.STRING)
                                                .description("회원 생년월일"),
                                        fieldWithPath("data.status").type(JsonFieldType.STRING)
                                                .description("회원 상태"),
                                        fieldWithPath("data.allowToMarketingNotification").type(JsonFieldType.BOOLEAN)
                                                .description("회원 마케팅수신동의여부")
                                )
                        )
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);


        ApiResponse<MemberResponse> result = objectMapper.readValue(response, new TypeReference<ApiResponse<MemberResponse>>() {
        });

        assertThat(target).isEqualTo(result);
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