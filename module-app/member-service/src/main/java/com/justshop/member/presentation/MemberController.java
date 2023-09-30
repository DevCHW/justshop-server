package com.justshop.member.presentation;

import com.justshop.member.application.SignUpService;
import com.justshop.member.presentation.dto.SignUpRequest;
import com.justshop.member.presentation.dto.mapper.ServiceLayerDtoMapper;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final SignUpService signUpService;

    // 회원가입
    @PostMapping
    public ApiResponse signUp(@RequestBody @Valid SignUpRequest request) {
        signUpService.signUp(ServiceLayerDtoMapper.mapping(request));

        return ApiResponse.created();
    }

    // TODO: Rest docs 설정

    // TODO: 내정보 조회

    // TODO: 로그인

    // TODO: 로그아웃

    // TODO: 회원 탈퇴

    // TODO: 내정보 수정

    // TODO: 내정보 조회

}
