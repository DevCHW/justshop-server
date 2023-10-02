package com.justshop.member.api.member.presentation;

import com.justshop.member.api.member.application.MemberService;
import com.justshop.member.api.member.application.dto.MemberResponse;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // TODO: 내정보 조회
    @GetMapping("/{memberId}")
    public ApiResponse<MemberResponse> me(@PathVariable Long memberId) {

        MemberResponse memberResponse = memberService.getMemberInfo(memberId);
        return ApiResponse.ok();
    }

    // 비밀번호 변경


    // TODO: 회원 탈퇴

    // TODO: 내정보 수정

    // TODO: 내정보 조회
}
