package com.justshop.order.client.reader;

import com.justshop.core.exception.BusinessException;
import com.justshop.order.client.ProductServiceClient;
import com.justshop.order.client.response.OrderProductInfo;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.justshop.core.error.ErrorCode.ORDER_FAIL;
import static com.justshop.order.api.order.application.dto.request.CreateOrderServiceRequest.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductServiceClient productServiceClient;

    public List<OrderProductInfo> read(List<OrderProductRequest> orderProductRequests) {
        MultiValueMap<String, Long> productOptionIds = new LinkedMultiValueMap<>();
        orderProductRequests.forEach(op ->
                productOptionIds.add("productOptionId", op.getProductOptionId())
        );
        try {
            return productServiceClient.getOrderProductInfo(productOptionIds).getData();
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new BusinessException(ORDER_FAIL, "상품 시스템 장애로 인하여 주문에 실패하였습니다.");
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw new BusinessException(ORDER_FAIL, "주문 상품 조회에 실패하여 주문에 실패하였습니다.");
        }
    }
}
