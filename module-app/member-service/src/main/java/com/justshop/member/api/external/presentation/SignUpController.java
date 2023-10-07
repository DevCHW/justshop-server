package com.justshop.member.api.external.presentation;

import com.justshop.member.api.external.application.SignUpService;
import com.justshop.member.api.external.presentation.dto.request.SignUpRequest;
import com.justshop.member.api.external.presentation.dto.mapper.ServiceLayerDtoMapper;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class SignUpController {

    private final SignUpService signUpService;

    // 회원가입
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse signUp(@RequestBody @Valid SignUpRequest request) {
        signUpService.signUp(ServiceLayerDtoMapper.mapping(request));
        return ApiResponse.created();
    }

}
