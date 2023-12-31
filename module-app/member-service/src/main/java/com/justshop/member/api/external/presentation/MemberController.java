package com.justshop.member.api.external.presentation;

import com.justshop.member.api.external.application.MemberService;
import com.justshop.member.api.external.presentation.dto.mapper.ServiceLayerDtoMapper;
import com.justshop.member.api.external.presentation.dto.request.CreateMemberRequest;
import com.justshop.member.api.external.presentation.dto.request.UpdateNicknameRequest;
import com.justshop.member.api.external.presentation.dto.request.UpdatePasswordRequest;
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

    // 비밀번호 변경
    @PatchMapping("/{memberId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse passwordEdit(@PathVariable Long memberId,
                                    @RequestBody @Valid UpdatePasswordRequest request) {
        memberService.editPassword(memberId, request.getPassword());
        return ApiResponse.noContent();
    }

    // 회원 탈퇴
    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse delete(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResponse.noContent();
    }

    // 닉네임 변경
    @PatchMapping("/{memberId}/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse updateMyInfo(@PathVariable Long memberId,
                                    @RequestBody UpdateNicknameRequest request) {
        memberService.editNickname(memberId, request.getNickname());
        return ApiResponse.noContent();
    }

    // 닉네임 중복확인
    @GetMapping("/nickname/exists/{nickname}")
    public ApiResponse<Boolean> existsNickname(@PathVariable String nickname) {
        Boolean existsNickname = memberService.existsNickname(nickname);
        return ApiResponse.ok(existsNickname);
    }

    // 이메일 중복확인
    @GetMapping("/email/exists/{email}")
    public ApiResponse<Boolean> existsEmail(@PathVariable String email) {
        Boolean existsEmail = memberService.existsEmail(email);
        return ApiResponse.ok(existsEmail);
    }

    // 회원가입
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse signUp(@RequestBody @Valid CreateMemberRequest request) {
        memberService.signUp(ServiceLayerDtoMapper.mapping(request));
        return ApiResponse.created();
    }

}
