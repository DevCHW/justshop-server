package com.justshop.product.api.external.application;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.core.kafka.message.order.OrderCreate.OrderQuantity;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.repository.ProductOptionRepository;
import com.justshop.product.domain.repository.ProductRepository;
import com.justshop.product.domain.repository.querydsl.dto.ProductDto;
import com.justshop.product.domain.repository.querydsl.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    /* 상품 상세 조회 */
    public ProductResponse getProductInfo(Long productId) {
        ProductDto productDto = productRepository.findProductDto(productId);
        return ProductResponse.of(productDto);
    }

    /* 상품 재고수량 감소 */
    @Transactional
    public void decreaseStock(OrderCreate message) {
        List<Long> productOptionIds = message.getOrderQuantities().stream()
                .map(OrderQuantity::getProductOptionId)
                .collect(Collectors.toList());

        Map<Long, Long> orderQuntityMap = extractOrderQuantityMap(message.getOrderQuantities());

        List<ProductOption> productOptions = productOptionRepository.findAllByIdIn(productOptionIds);

        productOptions.forEach (
                productOption -> {
                    Long quantity = orderQuntityMap.get(productOption.getId());
                    if (productOption.getStockQuantity() < quantity) {
                        throw new BusinessException(ErrorCode.NOT_ENOUGH_STOCK, "주문 수량보다 재고가 부족합니다.");
                    }
                    productOption.decreaseStock(quantity);
                }
        );
    }

    private Map<Long, Long> extractOrderQuantityMap(List<OrderQuantity> orderQuantities) {
        return orderQuantities.stream()
                .collect(Collectors.toMap(
                        OrderQuantity::getProductOptionId,
                        orderQuantity -> Long.valueOf(orderQuantity.getQuantity()))
                );
    }

    /* 상품목록 페이징 조회 */
    public Page<ProductResponse> getProductsForPage(SearchCondition searchCondition, Pageable pageable) {
        Page<Product> pageResult = productRepository.findProductsPageBy(searchCondition, pageable);
        List<ProductResponse> content = pageResult.getContent()
                .stream().map(product -> ProductResponse.from(product))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, pageResult.getTotalElements());
    }

}
