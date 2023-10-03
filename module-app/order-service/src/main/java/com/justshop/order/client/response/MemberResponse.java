package com.justshop.order.client.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

}
