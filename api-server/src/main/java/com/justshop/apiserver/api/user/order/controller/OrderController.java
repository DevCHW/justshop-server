package com.justshop.apiserver.api.user.order.controller;

import com.justshop.apiserver.config.response.ApiResponse;
import com.justshop.apiserver.api.user.order.controller.dto.request.CreateOrderRequest;
import com.justshop.apiserver.api.user.order.service.OrderService;
import com.justshop.apiserver.api.user.order.service.dto.CreateOrderResponse;
import com.justshop.apiserver.api.user.order.service.dto.CreateOrderServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<CreateOrderResponse> order(@RequestBody @Valid CreateOrderRequest request) {
        return ApiResponse.of(orderService.businessOrder(CreateOrderServiceRequest.from(request)), HttpStatus.CREATED);
    }
}
