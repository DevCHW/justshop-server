package com.justshop.apiserver.user.member.presentation;

import com.justshop.apiserver.common.response.ApiResponse;
import com.justshop.apiserver.user.member.presentation.dto.request.CreateMemberRequest;
import com.justshop.apiserver.user.member.application.dto.response.CreateMemberResponse;
import com.justshop.apiserver.user.member.application.MemberAuthService;
import com.justshop.apiserver.user.member.application.dto.request.CreateMemberServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberAuthController {

    private final MemberAuthService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateMemberResponse> singUp(@RequestBody @Valid CreateMemberRequest request) {
        return ApiResponse.of(memberService.signUp(CreateMemberServiceRequest.from(request)), HttpStatus.CREATED);
    }

}
