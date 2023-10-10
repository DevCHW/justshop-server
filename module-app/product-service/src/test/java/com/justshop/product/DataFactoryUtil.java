package com.justshop.product;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.ProductCategory;
import com.justshop.product.domain.entity.ProductImage;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;

public class DataFactoryUtil {

    private DataFactoryUtil() {
    }

    public static ProductCategory generateProductCategory(Product product, Long categoryId) {
        return new ProductCategory(product, categoryId);
    }

    public static ProductImage generateProductImage(Long fileId, boolean basicYn) {
        return ProductImage.builder()
                .fileId(fileId)
                .basicYn(basicYn)
                .build();
    }

    public static Product generateProduct() {
        Product product =  Product.builder()
                .name("Test Product")
                .price(10000)
                .salesQuantity(10000L)
                .likeCount(10000L)
                .reviewCount(10000L)
                .status(SellingStatus.SELLING)
                .gender(Gender.FREE)
                .build();

        product.addProductCategory(1L);
        product.addProductCategory(2L);
        product.addProductCategory(3L);
        product.addProductOption(Size.S, Color.BLACK, null, 0, 100);
        product.addProductOption(Size.M, Color.BLACK, null, 0, 200);
        product.addProductOption(Size.L, Color.BLACK, null, 0, 300);
        product.addProductOption(Size.S, Color.WHITE, null, 0, 300);
        product.addProductOption(Size.M, Color.WHITE, null, 0, 400);
        product.addProductOption(Size.L, Color.WHITE, null, 0, 500);
        product.addProductImage(1L, true);
        product.addProductImage(2L, false);
        product.addProductImage(3L, false);
        product.addProductImage(4L, false);
        product.addProductImage(5L, false);
        product.addProductDetail("Test Product Description .");
        return product;
    }
}
