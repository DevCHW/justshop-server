package com.justshop.member.api.member.presentation;

import com.justshop.member.RestDocsSupport;
import com.justshop.member.api.member.application.MemberService;
import com.justshop.member.api.member.application.dto.MemberResponse;
import com.justshop.member.entity.enums.Gender;
import com.justshop.member.entity.enums.MemberStatus;
import com.justshop.member.entity.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends RestDocsSupport {

    private MemberService memberService = Mockito.mock(MemberService.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }
    
    @DisplayName("회원은 본인의 정보를 조회할 수 있다.")
    @Test
    void getMyInfo() throws Exception {
        // given
        Long memberId = 1L;
        String email = "testEmail@Google.com";
        String name = "TestName";
        String nickname = "TestNickname";
        int point = 0;
        LocalDate birthday = LocalDate.of(1997, 1, 3);
        Role memberRole = Role.USER;
        Gender gender = Gender.MAN;
        MemberStatus status = MemberStatus.ACTIVE;

        MemberResponse response = MemberResponse.builder()
                .memberId(memberId)
                .email(email)
                .name(name)
                .nickname(nickname)
                .point(point)
                .birthday(birthday)
                .memberRole(memberRole)
                .gender(gender)
                .status(status)
                .build();

        given(memberService.getMemberInfo(memberId)).willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/v1/members/{memberId}", memberId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.memberId").value(1L))
                .andExpect(jsonPath("$.data.email").value("testEmail@Google.com"))
                .andExpect(jsonPath("$.data.name").value("TestName"))
                .andExpect(jsonPath("$.data.nickname").value("TestNickname"))
                .andExpect(jsonPath("$.data.point").value(0))
                .andExpect(jsonPath("$.data.birthday").value("1997-01-03"))
                .andExpect(jsonPath("$.data.memberRole").value("USER"))
                .andExpect(jsonPath("$.data.gender").value("MAN"))
                .andExpect(jsonPath("$.data.status").value("ACTIVE"))
                .andDo(document("member-get-my-info",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data.point").type(JsonFieldType.NUMBER).description("포인트"),
                                        fieldWithPath("data.birthday").type(JsonFieldType.STRING).description("생년월일"),
                                        fieldWithPath("data.memberRole").type(JsonFieldType.STRING).description("회원 권한"),
                                        fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data.status").type(JsonFieldType.STRING).description("회원 상태")
                                )
                        )
                );
    }
    
    @DisplayName("회원은 비밀번호를 변경할 수 있다.")
    @Test
    void passwordEdit() throws Exception {
        // given
        Long memberId = 1L;
        PasswordRequest request = new PasswordRequest("changePassword$");

        // when & then
        mockMvc.perform(
                        patch("/api/v1/members/{memberId}/password", memberId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andDo(document("member-update-password",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                requestFields(
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                );
    }
    

}