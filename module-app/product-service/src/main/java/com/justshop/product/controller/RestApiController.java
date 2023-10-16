package com.justshop.product.controller;

import com.justshop.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RestApiController {

    @GetMapping("/health_check")
    public ApiResponse<String> healthCheck() {
        return ApiResponse.ok("ok");
    }
}
