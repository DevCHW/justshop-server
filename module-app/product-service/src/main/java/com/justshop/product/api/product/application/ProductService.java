package com.justshop.product.api.product.application;

import com.justshop.product.api.product.application.dto.response.ProductPriceResponse;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.repository.ProductOptionRepository;
import com.justshop.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    // 상품 옵션 ID 리스트를 받아서 상품 가격 정보 조회
    public List<ProductPriceResponse> getOrderProductsInfo(List<Long> productOptionIds) {
        List<ProductOption> productOptions = productOptionRepository.findAllByIdIn(productOptionIds);

        return productOptions.stream()
                .map(po -> ProductPriceResponse.from(po))
                .collect(Collectors.toList());
    }
}
