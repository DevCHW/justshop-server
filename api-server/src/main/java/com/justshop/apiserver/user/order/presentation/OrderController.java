package com.justshop.apiserver.user.order.presentation;

import com.justshop.apiserver.common.response.ApiResponse;
import com.justshop.apiserver.user.order.presentation.dto.request.CreateOrderRequest;
import com.justshop.apiserver.user.order.application.OrderService;
import com.justshop.apiserver.user.order.application.dto.CreateOrderResponse;
import com.justshop.apiserver.user.order.application.dto.CreateOrderServiceRequest;
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
