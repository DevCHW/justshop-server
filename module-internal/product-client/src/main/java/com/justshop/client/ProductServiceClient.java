package com.justshop.client;

import com.justshop.client.dto.OrderProductInfo;
import com.justshop.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@FeignClient(name = "product-service")
// TODO: API Gateway 로드밸런싱 적용시 url 삭제
@FeignClient(name = "product-service", url = "http://127.0.0.1:8087/api/v1/internal/products")
public interface ProductServiceClient {

    @GetMapping("/order-products")
    ApiResponse<List<OrderProductInfo>> getOrderProductInfo(@SpringQueryMap MultiValueMap<String, Long> params);

}
