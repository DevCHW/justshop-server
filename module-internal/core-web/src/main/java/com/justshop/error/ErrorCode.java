package com.justshop.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // MEMBER
    MEMBER_NOT_FOUND(404, "MEMBER-001", "회원을 찾을 수 없습니다."),
    UNAUTHORIZED(401, "MEMBER-002", "인증에 실패한 경우"),
    ROLE_NOT_EXISTS(403, "MEMBER-003", "권한이 부족한 경우"),

    // ORDER
    ORDER_NOT_FOUND(404, "ORDER-001", "주문을 찾을 수 없습니다."),
    ORDER_FAIL(404, "ORDER-002", "주문에 실패하였습니다."),
    NOT_ENOUGH_STOCK(422, "ORDER-003", "재고가 부족합니다."),
    STATUS_NOT_SELLING(422, "ORDER-004", "현재 판매중이 아닌 상품입니다."),

    // DELIVERY
    DELIVERY_NOT_FOUND(404, "DELIVERY-001", "배송을 찾을 수 없습니다."),

    // PRODUCT
    PRODUCT_NOT_FOUND(404,"PRODUCT-001", "상품을 찾을 수 없습니다."),
    PRODUCT_LIKE_NOT_FOUND(404, "PRODUCT-002", "상품 좋아요를 찾을 수 없습니다."),
    PRODUCT_LIKE_EXISTS_ALREADY(409, "PRODUCT-003", "상품 좋아요가 이미 존재합니다."),
    Product_OPTION_NOT_FOUND(404, "PRODUCT-004", "상품 옵션을 찾을 수 없습니다"),
    // POINT
    NOT_ENOUGH_POINT(422, "ORDER-005", "포인트가 부족합니다."),

    // MAIL
    SEND_MAIL_FAIL(422, "MAIL-001", "메일 전송에 실패하였습니다."),

    INTERNAL_SERVER_ERROR(500, "SERVER-001", "서버가 요청을 처리할 수 없습니다."),
    JsonParsingError(500, "SERVER-002", "JSON 파싱에 실패하였습니다.");

    private final int status;
    private final String code;
    private final String description;

    ErrorCode(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
