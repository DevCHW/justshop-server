package com.justshop.order.client.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberResponse {

    private Long memberId;
    private String email; // 이메일
    private String name; //이름
    private String nickname; // 닉네임
    private int point; // 포인트
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthday; // 생년월일
    private String memberRole; // 권한
    private String gender; // 성별
    private String status; // 상태

    @Builder
    public MemberResponse(Long memberId, String email, String name, String nickname, int point, LocalDate birthday, String memberRole, String gender, String status) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.point = point;
        this.birthday = birthday;
        this.memberRole = memberRole;
        this.gender = gender;
        this.status = status;
    }
}
