package com.justshop.member.api.external.presentation;

import com.justshop.member.RestDocsSupport;
import com.justshop.member.api.external.application.MemberService;
import com.justshop.member.api.external.application.dto.request.SignUpServiceRequest;
import com.justshop.member.api.external.presentation.dto.request.SignUpRequest;
import com.justshop.member.api.external.presentation.dto.request.UpdateNicknameRequest;
import com.justshop.member.api.external.presentation.dto.request.UpdatePasswordRequest;
import com.justshop.member.domain.entity.enums.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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

    @DisplayName("회원가입에 필요한 정보를 받아서 회원가입을 할 수 있다.")
    @Test
    void signUp() throws Exception {
        // given
        SignUpRequest request = SignUpRequest.builder()
                .email("testEmail@naver.com")
                .password("testPassword")
                .name("testName")
                .nickname("testNickname")
                .birthday(LocalDate.of(1997, 1, 3))
                .gender(Gender.MAN)
                .build();

        Long createMemberId = 1L;
        given(memberService.signUp(any(SignUpServiceRequest.class))).willReturn(createMemberId);

        // when & then
        mockMvc.perform(
                        post("/api/v1/members")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andDo(document("member-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("birthday").type(JsonFieldType.STRING).description("생년월일"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                );
    }
    
    @DisplayName("회원은 비밀번호를 변경할 수 있다.")
    @Test
    void edit_password() throws Exception {
        // given
        Long memberId = 1L;
        UpdatePasswordRequest request = new UpdatePasswordRequest("changePassword$");

        // when & then
        mockMvc.perform(
                        patch("/api/v1/members/{memberId}/password", memberId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"))
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

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    void edit_nickname() throws Exception {
        // given
        Long memberId = 1L;
        UpdateNicknameRequest request = new UpdateNicknameRequest("changeNickname");

        // when & then
        mockMvc.perform(
                        patch("/api/v1/members/{memberId}/nickname", memberId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"))
                .andDo(document("member-update-nickname",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                requestFields(
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                );
    }

    @DisplayName("회원은 회원탈퇴를 할 수 있다.")
    @Test
    void delete_member() throws Exception {
        // given
        Long memberId = 1L;

        // when & then
        mockMvc.perform(
                        delete("/api/v1/members/{memberId}", memberId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"))
                .andDo(document("member-delete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                );
    }

    @DisplayName("닉네임의 존재여부를 조회한다.")
    @Test
    void exists_nickname() throws Exception {
        // given
        String nickname = "exists_nickname";

        given(memberService.existsNickname(anyString())).willReturn(true);

        // when & then
        mockMvc.perform(
                        get("/api/v1/members/nickname/exists/{nickname}", nickname))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andDo(document("member-get-exists-nickname",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("nickname").description("nickname")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("존재 여부")
                                )
                        )
                );
    }

    @DisplayName("이메일 존재여부를 조회한다.")
    @Test
    void exists_email() throws Exception {
        // given
        String email = "exists@google.com";
        given(memberService.existsEmail(anyString())).willReturn(true);

        // when & then
        mockMvc.perform(
                        get("/api/v1/members/email/exists/{email}", email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andDo(document("member-get-exists-email",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("email").description("email")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("존재 여부")
                                )
                        )
                );
    }

}