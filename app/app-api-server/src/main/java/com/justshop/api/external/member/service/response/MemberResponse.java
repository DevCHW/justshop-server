package com.justshop.api.external.member.service.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justshop.domain.member.domain.Member;
import com.justshop.domain.member.domain.enumerated.Gender;
import com.justshop.domain.member.domain.enumerated.MemberStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class MemberResponse {

    private Long id;
    private String name;
    private String email;
    private Gender gender;

    // TODO : @JsonFormat, @DateTimeFormat 동작원리 알아보기
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private MemberStatus status;
    private boolean allowToMarketingNotification;

    @Builder
    public MemberResponse(Long id, String name, String email, Gender gender, LocalDate birthday, MemberStatus status, boolean allowToMarketingNotification) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.status = status;
        this.allowToMarketingNotification = allowToMarketingNotification;
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .gender(member.getGender())
                .birthday(member.getBirthday())
                .status(member.getStatus())
                .allowToMarketingNotification(member.isAllowToMarketingNotification())
                .build();
    }

}
