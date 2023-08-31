package com.justshop.api.external.member.controller.request;

import com.justshop.domain.member.domain.enumerated.Gender;
import com.justshop.domain.member.domain.enumerated.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    private String loginId;
    private String password;
    private String name;
    private String email;
    private Gender gender;
    private LocalDate birthday;
    private MemberStatus status;
    private boolean allowToMarketingNotification;

    @Builder
    public MemberCreateRequest(String loginId, String password, String name, String email, Gender gender, LocalDate birthday, MemberStatus status, boolean allowToMarketingNotification) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.status = status;
        this.allowToMarketingNotification = allowToMarketingNotification;
    }

}
