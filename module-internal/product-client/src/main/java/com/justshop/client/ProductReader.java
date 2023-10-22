package com.justshop.client;

import com.justshop.client.dto.OrderProductInfo;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductServiceClient productServiceClient;

    public List<OrderProductInfo> read(MultiValueMap<String, Long> productOptionIds) {
        try {
            return productServiceClient.getOrderProductInfo(productOptionIds).getData();
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.ORDER_FAIL, "상품 시스템 장애로 인하여 주문에 실패하였습니다.");
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.ORDER_FAIL, "주문 상품 조회에 실패하여 주문에 실패하였습니다.");
        }
    }
}
