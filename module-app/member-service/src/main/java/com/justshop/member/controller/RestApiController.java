package com.justshop.member.controller;

import com.justshop.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RestApiController {

    @Autowired
    private Environment env;

    @GetMapping("/health_check")
    public ApiResponse<String> healthCheck() {
        return ApiResponse.ok("ok");
    }

    @GetMapping("/test")
    public String test() {
        String message = env.getProperty("test.prod.string");
        return message;
    }
}
