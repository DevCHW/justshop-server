package com.justshop.apiserver.api.user.member.controller;

import com.justshop.apiserver.api.user.member.service.MemberService;
import com.justshop.apiserver.api.user.member.service.dto.response.MemberResponse;
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

    // 내정보 조회
    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> getMyInfo(@PathVariable Long id) {
        return ApiResponse.ok(memberService.getMyInfo(id));
    }

}
