package com.justshop.order.api.order.presentation;

import com.justshop.order.api.order.application.OrderService;
import com.justshop.order.api.order.application.dto.response.OrderResponse;
import com.justshop.order.api.order.presentation.dto.mapper.ServiceLayerDtoMapper;
import com.justshop.order.api.order.presentation.dto.request.CreateOrderRequest;
import com.justshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 API
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse order(@RequestBody @Valid CreateOrderRequest request) {
        orderService.order(ServiceLayerDtoMapper.mapping(request));
        return ApiResponse.created();
    }

    // 주문 정보 조회
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable Long orderId) {
        OrderResponse response = orderService.getOrder(orderId);
        return ApiResponse.ok(response);
    }

}
