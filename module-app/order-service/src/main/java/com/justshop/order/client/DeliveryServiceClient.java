package com.justshop.order.client;

import com.justshop.order.client.response.DeliveryResponse;
import com.justshop.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

//@FeignClient(name = "member-service")
// TODO: API Gateway 로드밸런싱 적용시 url 삭제
@FeignClient(name = "delivery-service", url = "http://127.0.0.1:8083/api/v1/deliveries")
public interface DeliveryServiceClient {

    @GetMapping
    ApiResponse<DeliveryResponse> getDelivery(@SpringQueryMap Map<String, Long> deliveryId);

}
