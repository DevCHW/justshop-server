package com.justshop.product.api.order.application;

import com.justshop.product.api.product.application.dto.response.OrderProductInfo;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductOptionRepository productOptionRepository;
    // 상품 옵션 ID 리스트를 받아서 상품 가격 정보 조회
    public List<OrderProductInfo> getOrderProductsInfo(List<Long> productOptionIds) {
        List<ProductOption> productOptions = productOptionRepository.findAllByIdIn(productOptionIds);

        return productOptions.stream()
                .map(po -> OrderProductInfo.from(po))
                .collect(Collectors.toList());
    }
}
