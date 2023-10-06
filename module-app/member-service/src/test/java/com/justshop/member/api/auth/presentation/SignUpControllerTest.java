package com.justshop.member.api.auth.presentation;

import com.justshop.member.RestDocsSupport;
import com.justshop.member.api.auth.application.SignUpService;
import com.justshop.member.api.auth.application.dto.request.SignUpServiceRequest;
import com.justshop.member.api.auth.presentation.dto.request.SignUpRequest;
import com.justshop.member.domain.entity.enums.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SignUpControllerTest extends RestDocsSupport {
//    @MockBean
//    private SignUpService signUpService;

    private final SignUpService signUpService = mock(SignUpService.class);

    @Override
    protected Object initController() {
        return new SignUpController(signUpService);
    }

    @DisplayName("회원가입을 할 수 있다.")
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
        given(signUpService.signUp(any(SignUpServiceRequest.class))).willReturn(createMemberId);

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
}