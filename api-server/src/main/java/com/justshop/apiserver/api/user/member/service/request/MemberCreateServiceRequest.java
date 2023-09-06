package com.justshop.apiserver.api.user.member.service.request;

import com.justshop.apiserver.api.user.member.controller.request.MemberCreateRequest;
import com.justshop.core.member.domain.Member;
import com.justshop.core.member.domain.enums.Gender;
import com.justshop.core.member.domain.enums.MemberStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberCreateServiceRequest {

    private String loginId;
    private String password;
    private String name;
    private String email;
    private Gender gender;
    private LocalDate birthday;
    private MemberStatus status;
    private boolean allowToMarketingNotification;

    @Builder
    public MemberCreateServiceRequest(String loginId, String password, String name, String email, Gender gender, LocalDate birthday, MemberStatus status, boolean allowToMarketingNotification) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.status = status;
        this.allowToMarketingNotification = allowToMarketingNotification;
    }

    public static MemberCreateServiceRequest of(MemberCreateRequest memberCreateRequest) {
        return MemberCreateServiceRequest.builder()
                .loginId(memberCreateRequest.getLoginId())
                .password(memberCreateRequest.getPassword())
                .name(memberCreateRequest.getName())
                .email(memberCreateRequest.getEmail())
                .gender(memberCreateRequest.getGender())
                .birthday(memberCreateRequest.getBirthday())
                .status(memberCreateRequest.getStatus())
                .allowToMarketingNotification(memberCreateRequest.isAllowToMarketingNotification())
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .gender(this.gender)
                .birthday(this.birthday)
                .allowToMarketingNotification(this.allowToMarketingNotification)
                .build();
    }
}
