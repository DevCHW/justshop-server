package com.justshop.product.api.external.application;

import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.product.DataFactoryUtil;
import com.justshop.product.api.external.application.dto.response.ProductResponse;
import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import com.justshop.product.domain.repository.ProductOptionRepository;
import com.justshop.product.domain.repository.ProductRepository;
import com.justshop.product.domain.repository.querydsl.dto.SearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @DisplayName("상품 ID를 받아서 상품 상세조회를 할 수 있다.")
    @Test
    void getProductInfo_success() {
        // given
        Product product = productRepository.save(DataFactoryUtil.generateProduct());
        Long productId = product.getId();

        // when
        ProductResponse result = productService.getProductInfo(productId);

        // then
        assertThat(result.getProductId()).isEqualTo(product.getId());
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getPrice()).isEqualTo(product.getPrice());
        assertThat(result.getSalesQuantity()).isEqualTo(product.getSalesQuantity());
        assertThat(result.getLikeCount()).isEqualTo(product.getLikeCount());
        assertThat(result.getReviewCount()).isEqualTo(product.getReviewCount());
        assertThat(result.getStatus()).isEqualTo(product.getStatus());
        assertThat(result.getGender()).isEqualTo(product.getGender());
        assertThat(result.getDetail()).isEqualTo(product.getProductDetail().getDescription());
        assertThat(result.getImages().size()).isEqualTo(product.getProductImages().size());
        assertThat(result.getOptions().size()).isEqualTo(product.getProductOptions().size());
    }

    @DisplayName("주문 생성 메세지를 받아서 재고를 감소시킬 수 있다.")
    @Test
    void decreaseStock_success() {
        // given
        Product product = DataFactoryUtil.generateProduct();
        productRepository.save(product);

        Long productOptionId1 = product.getProductOptions().get(0).getId();
        int stockQuantity1 = product.getProductOptions().get(0).getStockQuantity();

        Long productOptionId2 = product.getProductOptions().get(1).getId();
        int stockQuantity2 = product.getProductOptions().get(1).getStockQuantity();

        int quantity1 = 100;
        int quantity2 = 200;
        OrderCreate.OrderQuantity orderQuantity1 = new OrderCreate.OrderQuantity(productOptionId1, quantity1);
        OrderCreate.OrderQuantity orderQuantity2 = new OrderCreate.OrderQuantity(productOptionId2, quantity2);
        List<OrderCreate.OrderQuantity> orderQuantities = List.of(orderQuantity1, orderQuantity2);

        OrderCreate orderCreate = OrderCreate.builder()
                .orderId(1L)
                .memberId(1L)
                .couponId(1L)
                .usePoint(1000L)
                .payAmount(100000L)
                .orderQuantities(orderQuantities)
                .build();

        // when
        productService.decreaseStock(orderCreate);
        ProductOption result1 = productOptionRepository.findById(productOptionId1).get();
        ProductOption result2 = productOptionRepository.findById(productOptionId2).get();

        // then
        assertThat(result1.getStockQuantity()).isEqualTo((stockQuantity1 - quantity1));
        assertThat(result2.getStockQuantity()).isEqualTo((stockQuantity2 - quantity2));
    }

    @DisplayName("주문 생성 메세지를 받아서 재고를 감소시킬 때 재고가 부족하다면 BusinessException이 발생한다.")
    @Test
    void decreaseStock_fail() {
        // given
        Product product = DataFactoryUtil.generateProduct();
        productRepository.save(product);

        Long productOptionId1 = product.getProductOptions().get(0).getId();
        int stockQuantity1 = product.getProductOptions().get(0).getStockQuantity();

        Long productOptionId2 = product.getProductOptions().get(1).getId();
        int stockQuantity2 = product.getProductOptions().get(1).getStockQuantity();

        int quantity1 = stockQuantity1 + 1;
        int quantity2 = stockQuantity2 + 1;
        OrderCreate.OrderQuantity orderQuantity1 = new OrderCreate.OrderQuantity(productOptionId1, quantity1);
        OrderCreate.OrderQuantity orderQuantity2 = new OrderCreate.OrderQuantity(productOptionId2, quantity2);
        List<OrderCreate.OrderQuantity> orderQuantities = List.of(orderQuantity1, orderQuantity2);

        OrderCreate orderCreate = OrderCreate.builder()
                .orderId(1L)
                .memberId(1L)
                .couponId(1L)
                .usePoint(1000L)
                .payAmount(100000L)
                .orderQuantities(orderQuantities)
                .build();

        // when & then
        assertThatThrownBy(() -> productService.decreaseStock(orderCreate))
                .isInstanceOf(BusinessException.class)
                .hasMessage("주문 수량보다 재고가 부족합니다.");
    }

    @DisplayName("검색 조건과 요청 페이지 정보를 받아서 상품목록을 페이징 처리하여 반환한다.")
    @Test
    void getProductsForPage_success() {
        // given
        SearchCondition searchCondition = SearchCondition.builder()
                .name("")
                .minPrice(0)
                .maxPrice(2147483647)
                .status(null)
                .gender(null)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ProductResponse> result = productService.getProductsForPage(searchCondition, pageable);

        // then
        assertThat(result.getContent()).hasSize(10);
    }

    @DisplayName("카테고리 ID와 검색조건, 페이지 정보를 받아서 해당 카테고리의 상품 목록을 페이징 처리하여 반환한다.")
    @Test
    void getProductsCategoryForPage_success() {
        // given
        Long categoryId = 1L;
        for(int i=0; i<11; i++) {
            productRepository.save(generateProductSetCategoryId(categoryId));
        }

        SearchCondition searchCondition = SearchCondition.builder()
                .name("")
                .minPrice(0)
                .maxPrice(2147483647)
                .status(null)
                .gender(null)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ProductResponse> result = productService.getProductsCategoryForPage(categoryId, searchCondition, pageable);

        // then
        assertThat(result.getContent()).hasSize(10);
    }

    public Product generateProductSetCategoryId(long categoryId) {
        Product product =  Product.builder()
                .name("Test Product")
                .price(10000)
                .salesQuantity(10000L)
                .likeCount(10000L)
                .reviewCount(10000L)
                .status(SellingStatus.SELLING)
                .gender(Gender.FREE)
                .build();
        product.addProductCategory(categoryId);
        product.addProductOption(Size.S, Color.BLACK, null, 0, 100);
        product.addProductImage(UUID.randomUUID()+".PNG", "상품이미지5.PNG", "C://file/product/images", false);
        product.addProductDetail("Test Product Description .");
        return product;
    }

}