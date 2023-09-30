package com.justshop.member.api.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "로그인 아이디는 필수값입니다.")
    private String email;

    @NotBlank(message = "패스워드는 필수값입니다.")
    private String password;

}
