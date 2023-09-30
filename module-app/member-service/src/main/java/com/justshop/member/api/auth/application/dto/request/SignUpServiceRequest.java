package com.justshop.member.api.auth.application.dto.request;

import com.justshop.member.entity.Member;
import com.justshop.member.entity.enums.Gender;
import com.justshop.member.entity.enums.MemberStatus;
import com.justshop.member.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
public class SignUpServiceRequest {

    private String email; //이메일
    private String password; //비밀번호
    private String name; //이름
    private String nickname; //닉네임
    private LocalDate birthday; //생년월일
    private Gender gender; //성별

    @Builder
    public SignUpServiceRequest(String email, String password, String name, String nickname, LocalDate birthday, Gender gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
    }

    // Dto -> Entity
    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .point(0)
                .birthday(birthday)
                .memberRole(Role.USER)
                .gender(gender)
                .status(MemberStatus.ACTIVE)
                .build();
    }

}
