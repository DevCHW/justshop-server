package com.justshop.member.api.member.presentation;

import com.justshop.member.api.member.application.MemberService;
import com.justshop.member.api.member.application.dto.MemberResponse;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    // 내정보 조회
    @GetMapping("/{memberId}")
    public ApiResponse<MemberResponse> getMyInfo(@PathVariable Long memberId) {
        MemberResponse response = memberService.getMemberInfo(memberId);
        return ApiResponse.ok(response);
    }

    // 비밀번호 변경
    // TODO : 비밀번호 하나만 변경인데, PUT이 맞는지 PATCH가 맞는지 알아보고 결정하기
    @PatchMapping("/{memberId}/password")
    public ApiResponse passwordEdit(@PathVariable Long memberId,
                                    @RequestBody @Valid PasswordRequest request) {
        memberService.passwordEdit(memberId, request.getPassword());
        return ApiResponse.ok();
    }

    // TODO: 회원 탈퇴

    // TODO: 내정보 수정

    // TODO: 내정보 조회
}
