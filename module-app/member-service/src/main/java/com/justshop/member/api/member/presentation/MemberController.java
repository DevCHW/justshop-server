package com.justshop.member.api.member.presentation;

import com.justshop.member.api.member.application.MemberService;
import com.justshop.member.api.member.application.dto.MemberResponse;
import com.justshop.member.api.member.presentation.dto.UpdateNicknameRequest;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse passwordEdit(@PathVariable Long memberId,
                                    @RequestBody @Valid UpdatePasswordRequest request) {
        memberService.editPassword(memberId, request.getPassword());
        return ApiResponse.noContent();
    }

    // 회원 탈퇴 TODO: 테스트 작성
    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse delete(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResponse.noContent();
    }

    // 닉네임 변경 TODO: 테스트 작성
    @PatchMapping("/{memberId}/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse updateMyInfo(@PathVariable Long memberId,
                                    @RequestBody UpdateNicknameRequest request) {
        memberService.editNickname(memberId, request.getNickname());
        return ApiResponse.noContent();
    }

    // 닉네임 중복확인 TODO: 테스트 작성
    @GetMapping("/nickname/exists/{nickname}")
    public ApiResponse<Boolean> existsNickname(@PathVariable String nickname) {
        Boolean existsNickname = memberService.existsNickname(nickname);
        return ApiResponse.ok(existsNickname);
    }

    // 이메일 중복확인 TODO: 테스트 작성
    @GetMapping("/email/exists/{email}")
    public ApiResponse<Boolean> existsEmail(@PathVariable String email) {
        Boolean existsEmail = memberService.existsEmail(email);
        return ApiResponse.ok(existsEmail);
    }

}
