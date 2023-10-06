package com.justshop.order.client.reader;

import com.justshop.core.exception.BusinessException;
import com.justshop.order.client.DeliveryServiceClient;
import com.justshop.order.client.response.DeliveryResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static com.justshop.core.error.ErrorCode.ORDER_CANCEL_FAIL;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryReader {

    private final DeliveryServiceClient deliveryServiceClient;

    public DeliveryResponse read(Long orderId) {
        Map<String, Long> deliverySearchParam = new HashMap<>();
        deliverySearchParam.put("orderId", orderId);
        try {
            return deliveryServiceClient.getDelivery(deliverySearchParam).getData();
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new BusinessException(ORDER_CANCEL_FAIL, "배송 시스템 장애로 인하여 주문에 실패하였습니다.");
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw new BusinessException(ORDER_CANCEL_FAIL, "배송 정보 조회에 실패하여 주문에 실패하였습니다.");
        }
    }

}
