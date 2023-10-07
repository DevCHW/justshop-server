package com.justshop.member.api.external.presentation;

import com.justshop.member.api.external.application.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/auth")
public class AuthController {

    private final AuthService authService;

    // TODO: 이메일 인증

}
