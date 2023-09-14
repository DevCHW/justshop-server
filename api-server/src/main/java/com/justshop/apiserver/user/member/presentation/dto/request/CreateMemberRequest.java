package com.justshop.apiserver.user.member.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justshop.core.member.domain.enums.Gender;
import lombok.Builder;
import lombok.Getter;


import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateMemberRequest {

    @NotNull(message = "아이디는 필수입니다.")
    private String loginId;

    // TODO : 비밀번호 정규표현식 맞는지 검증하기
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 영문 대 소문자, 숫자, 특수문자가 포함된 8~16자여야 합니다.")
    private String password;

    @NotBlank
    private String name;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @Size(min = 2, max = 10, message = "닉네임은 2~10자리여야 합니다.")
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotNull(message = "성별은 필수입니다.")
    private Gender gender;

    @NotBlank(message = "핸드폰 번호는 필수입니다.")
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthday;

    @NotNull(message = "이메일 수신동의 여부는 필수입니다.")
    private Boolean allowToMarketingNotification;

    @Builder
    public CreateMemberRequest(String loginId, String password, String name, String email, String nickname, Gender gender, String phone, LocalDate birthday, Boolean allowToMarketingNotification) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.phone = phone;
        this.birthday = birthday;
        this.allowToMarketingNotification = allowToMarketingNotification;
    }

}
