package com.justshop.core.product.implementation.command;

import com.justshop.core.common.ErrorCode;
import com.justshop.core.common.exception.BusinessException;
import com.justshop.core.product.domain.ProductOption;
import com.justshop.core.product.domain.repository.ProductOptionRepository;
import com.justshop.core.product.exception.NotEnoughStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StockManager {

    private final ProductOptionRepository productOptionRepository;

    // 재고 감소
    public void deductQuantity(List<Long> productIds, List<Long> optionIds) {
        List<ProductOption> productOptions = productOptionRepository.findAllByProductIdInAndOptionIdIn(productIds, optionIds);

        Set<ProductOption> removeDuplicatesProductOptions = new HashSet<>();
        // key = productOptionId, value = orderQuantity
        Map<Long, Integer> map = new HashMap<>();
        int len = productIds.size();
        int idx = 0;

        // N + 1 발생할수도 있음
        for (ProductOption productOption : productOptions) {
            Long productId = productOption.getProduct().getId();
            Long optionId = productOption.getOption().getId();

            if (productId.equals(productIds.get(idx)) && optionId.equals(optionIds.get(idx))) {
                if (idx >= len) {
                    throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "재고 감소 로직 에러");
                }
                map.put(productOption.getId(), map.getOrDefault(productOption.getId(), 0) + 1);
                removeDuplicatesProductOptions.add(productOption);
                idx++;
            }
        }

        // 주문 수량만큼 재고 감소시키기
        for (ProductOption productOption : removeDuplicatesProductOptions) {
            int orderQuantity = map.get(productOption.getId());

            if (productOption.getStockQuantity() < orderQuantity) {
                throw new NotEnoughStockException(ErrorCode.NOT_ENOUGH_STOCK, "재고보다 주문수량이 많습니다.");
            }
            productOption.deductStockQuantity(orderQuantity);
        }
    }

}
