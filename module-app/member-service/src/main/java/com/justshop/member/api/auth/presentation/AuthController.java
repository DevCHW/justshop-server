package com.justshop.member.api.auth.presentation;

import com.justshop.member.api.auth.application.SignUpService;
import com.justshop.member.api.auth.presentation.dto.request.SignUpRequest;
import com.justshop.member.api.auth.presentation.dto.mapper.ServiceLayerDtoMapper;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class AuthController {

    private final SignUpService signUpService;

    // 회원가입
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
