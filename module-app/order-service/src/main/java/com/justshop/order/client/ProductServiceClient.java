package com.justshop.order.client;

import com.justshop.order.client.response.ProductPriceResponse;
import com.justshop.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@FeignClient(name = "product-service")
// TODO: API Gateway 로드밸런싱 적용시 url 삭제
@FeignClient(name = "product-service", url = "http://127.0.0.1:8087/api/v1/products")
public interface ProductServiceClient {

    @GetMapping("/prices")
    ApiResponse<List<ProductPriceResponse>> getOrderPriceInfo(@SpringQueryMap MultiValueMap<String, Long> params);

}
