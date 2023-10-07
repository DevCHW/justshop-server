package com.justshop.product.api.external.application;

import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.core.kafka.message.order.OrderCreate.OrderQuantity;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.repository.ProductOptionRepository;
import com.justshop.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.justshop.core.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    // 상품 상세 조회
    public ProductResponse getProductInfo(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(PRODUCT_LIKE_NOT_FOUND));

        // TODO: 기능 마저 작성하기, 테스트 작성, 현재는 단순한 Product 정보만 return해줌.
        return ProductResponse.from(product);
    }

    // 상품 재고수량 감소
    @Transactional
    public void decreaseStock(OrderCreate message) {
        List<Long> productOptionIds = message.getOrderQuantities().stream()
                .map(OrderQuantity::getProductOptionId)
                .collect(Collectors.toList());

        Map<Long, Long> orderQuntityMap = generateOrderQuantityMap(message.getOrderQuantities());

        List<ProductOption> productOptions = productOptionRepository.findAllByIdIn(productOptionIds);

        productOptions.forEach (
                productOption -> {
                    Long quantity = orderQuntityMap.get(productOption.getId());
                    log.info("Decrease Stock, ID : {}, current stock : {}, decrease quantity : {}",
                            productOption.getId(),productOption.getStockQuantity(), quantity);
                    productOption.decreaseStock(quantity);
                }
        );
    }

    // KEY : ProductOptionId, Value : Quantity
    private Map<Long, Long> generateOrderQuantityMap(List<OrderQuantity> orderQuantities) {
        return orderQuantities.stream()
                .collect(Collectors.toMap(
                        OrderQuantity::getProductOptionId,
                        orderQuantity -> Long.valueOf(orderQuantity.getQuantity()))
                );
    }

}
