package com.justshop.member;

import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.entity.enums.Gender;
import com.justshop.member.domain.entity.enums.Role;

import java.time.LocalDate;

public class DataFactoryUtil {

    private DataFactoryUtil() {
    }

    public static Member generateMember() {
        String email = "testEmail@google.com";
        String password = "testPassword@";
        String name = "testName";
        String nickname = "testNickname";
        int point = 1000;
        LocalDate birthday = LocalDate.of(1997, 1, 3);
        Role memberRole = Role.USER;
        Gender gender = Gender.MAN;

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .point(point)
                .birthday(birthday)
                .memberRole(memberRole)
                .gender(gender)
                .build();
    }
}
