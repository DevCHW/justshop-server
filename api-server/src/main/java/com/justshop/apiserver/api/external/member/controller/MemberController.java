package com.justshop.apiserver.api.external.member.controller;

import com.justshop.apiserver.api.external.member.service.MemberService;
import com.justshop.apiserver.api.external.member.service.response.MemberResponse;
import com.justshop.apiserver.api.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 단건 조회
     */
    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> getOneMember(@PathVariable Long id) {
        return ApiResponse.ok(memberService.getOneMember(id));
    }

}
