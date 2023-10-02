package com.justshop.member.api.auth.presentation;

import com.justshop.member.api.auth.application.AuthService;
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
