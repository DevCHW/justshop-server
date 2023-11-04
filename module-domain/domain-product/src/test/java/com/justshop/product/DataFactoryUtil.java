package com.justshop.product;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.ProductCategory;
import com.justshop.product.domain.entity.ProductImage;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;

import java.util.UUID;

public class DataFactoryUtil {

    private DataFactoryUtil() {
    }

    public static ProductCategory generateProductCategory(Product product, Long categoryId) {
        return new ProductCategory(product, categoryId);
    }

    public static ProductImage generateProductImage(String saveFileName, String originFileName, String path, boolean basicYn) {
        return ProductImage.builder()
                .basicYn(basicYn)
                .saveFileName(saveFileName)
                .originFileName(originFileName)
                .path(path)
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
        product.addProductOption(new ProductOption(Size.S, Color.BLACK, null, 0, 100));
        product.addProductOption(new ProductOption(Size.M, Color.BLACK, null, 0, 200));
        product.addProductOption(new ProductOption(Size.L, Color.BLACK, null, 0, 300));
        product.addProductOption(new ProductOption(Size.S, Color.WHITE, null, 0, 300));
        product.addProductOption(new ProductOption(Size.M, Color.WHITE, null, 0, 400));
        product.addProductOption(new ProductOption(Size.L, Color.WHITE, null, 0, 500));
        product.addProductImage(new ProductImage(UUID.randomUUID()+".PNG", "상품이미지1.PNG", "C://file/product/images", true));
        product.addProductImage(new ProductImage(UUID.randomUUID()+".PNG", "상품이미지2.PNG", "C://file/product/images", false));
        product.addProductImage(new ProductImage(UUID.randomUUID()+".PNG", "상품이미지3.PNG", "C://file/product/images", false));
        product.addProductImage(new ProductImage(UUID.randomUUID()+".PNG", "상품이미지4.PNG", "C://file/product/images", false));
        product.addProductImage(new ProductImage(UUID.randomUUID()+".PNG", "상품이미지5.PNG", "C://file/product/images", false));
        product.addProductDetail("Test Product Description .");
        return product;
    }
}
