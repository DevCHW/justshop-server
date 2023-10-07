package com.justshop.member.api.external.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호의 길이는 8자이상 20자 이하여야 합니다.")
    private String password;

    public UpdatePasswordRequest(String password) {
        this.password = password;
    }
}
