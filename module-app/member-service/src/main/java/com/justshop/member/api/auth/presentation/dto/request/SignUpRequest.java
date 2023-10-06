package com.justshop.member.api.auth.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justshop.member.domain.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일은 필수값입니다.")
    @Email
    @Size(min = 2, message = "이메일은 2자 이상이어야 합니다.")
    private String email; //이메일

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호의 길이는 8자이상 20자 이하여야 합니다.")
    private String password; //비밀번호

    @NotBlank(message = "이름은 필수값입니다.")
    private String name; //이름

    @NotBlank(message = "닉네임은 필수값입니다.")
    private String nickname; //닉네임

    @NotNull(message = "생년월일은 필수값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthday; //생년월일

    @NotNull(message = "성별은 필수값입니다.")
    private Gender gender; //성별

    @Builder
    public SignUpRequest(String email, String password, String name, String nickname, LocalDate birthday, Gender gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
    }

}
