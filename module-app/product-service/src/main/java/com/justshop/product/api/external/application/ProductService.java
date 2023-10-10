package com.justshop.product.api.external.application;

import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.core.kafka.message.order.OrderCreate.OrderQuantity;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.client.reader.FileReader;
import com.justshop.product.client.response.FileResponse;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.repository.ProductOptionRepository;
import com.justshop.product.domain.repository.ProductRepository;
import com.justshop.product.domain.repository.querydsl.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final FileReader fileReader;

    /* 상품 상세 조회 */
    // TODO : 테스트 작성
    public ProductResponse getProductInfo(Long productId) {
        try {
            ProductDto productDto = productRepository.findProductDto(productId);
            List<Long> fileIds = extractFileIds(productDto.getImages());
            List<FileResponse> files = fileReader.read(fileIds);
            return ProductResponse.of(productDto, files);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Long> extractFileIds(List<ProductDto.ProductImageDto> images) {
        return images.stream()
                .map(ProductDto.ProductImageDto::getFileId)
                .collect(Collectors.toList());
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
                    log.info("Decrease Stock, ID : {}, current stock : {}, decrease quantity : {}",
                            productOption.getId(),productOption.getStockQuantity(), quantity);
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

}
