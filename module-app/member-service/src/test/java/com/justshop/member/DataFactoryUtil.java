package com.justshop.member;

import com.justshop.member.domain.entity.Address;
import com.justshop.member.domain.entity.DeliveryAddress;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.entity.enums.Gender;
import com.justshop.member.domain.entity.enums.MemberStatus;
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
        MemberStatus status = MemberStatus.ACTIVE;

        Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .point(point)
                .birthday(birthday)
                .memberRole(memberRole)
                .gender(gender)
                .status(status)
                .build();
        return member;
    }

    public static Member generateMemberWithDelivery() {
        String email = "testEmail@google.com";
        String password = "testPassword@";
        String name = "testName";
        String nickname = "testNickname";
        int point = 1000;
        LocalDate birthday = LocalDate.of(1997, 1, 3);
        Role memberRole = Role.USER;
        Gender gender = Gender.MAN;
        MemberStatus status = MemberStatus.ACTIVE;

        DeliveryAddress address = DeliveryAddress.builder()
                .basicYn(true)
                .address(new Address("test Street", "test City", "test Zipcode"))
                .build();

        Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .point(point)
                .birthday(birthday)
                .memberRole(memberRole)
                .gender(gender)
                .status(status)
                .build();
        member.addDeliveryAddress(address);
        return member;
    }
}
