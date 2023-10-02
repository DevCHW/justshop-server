package com.justshop.member.api.auth.presentation;

import com.justshop.member.RestDocsSupport;
import com.justshop.member.api.auth.application.AuthService;
import org.mockito.Mockito;


class AuthControllerTest extends RestDocsSupport {

    private final AuthService authService = Mockito.mock(AuthService.class);

    @Override
    protected Object initController() {
        return new AuthController(authService);
    }


}