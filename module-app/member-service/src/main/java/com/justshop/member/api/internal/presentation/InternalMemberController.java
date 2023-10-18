package com.justshop.member.api.internal.presentation;

import com.justshop.member.api.internal.application.InternalMemberService;
import com.justshop.member.api.internal.application.dto.MemberResponse;
import com.justshop.response.ApiResponse;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/internal/members")
@RequiredArgsConstructor
public class InternalMemberController {// 회원 정보 조회

    private final InternalMemberService internalMemberService;

    @GetMapping("/{memberId}")
    @Timed("members.getMemberInfo")
    public ApiResponse<MemberResponse> getMemberInfo(@PathVariable Long memberId) {
        MemberResponse response = internalMemberService.getMemberInfo(memberId);
        return ApiResponse.ok(response);
    }
}
