package com.justshop.error;

public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "COMMON-001", "유효성 검증에 실패한 경우"),
    FILE_UPLOAD_CONFLICT(409, "COMMON-002", "파일 업로드 중 실패한 경우"),

    // MEMBER
    MEMBER_NOT_FOUND(404, "MEMBER-001", "회원을 찾을 수 없는 경우"),
    UNAUTHORIZED(401, "MEMBER-002", "인증에 실패한 경우"),
    DUPLICATE_LOGIN_ID(400, "MEMBER-003", "계정명이 중복된 경우"),
    ROLE_NOT_EXISTS(403, "MEMBER-004", "권한이 부족한 경우"),
    TOKEN_NOT_EXISTS(404, "MEMBER-005", "해당 key의 인증 토큰이 존재하지 않는 경우"),
    DUPLICATE_PHONE_NUMBER(400, "MEMBER-006", "휴대폰 번호가 중복된 경우"),
    UNAVAILABLE_LOGIN_ID(400, "MEMBER-007", "사용 불가한 로그인 ID일 경우"),

    // ORDER
    ORDER_NOT_FOUND(404, "ORDER-001", "주문을 찾을 수 없는 경우"),
    NOT_ENOUGH_STOCK(422, "ORDER-002", "주문 수량보다 재고가 부족한 경우"),
    STATUS_NOT_SELLING(422, "ORDER-004", "주문 상품들이 판매상태가 아닌 경우"),

    // DELIVERY
    DELIVERY_NOT_FOUND(404, "DELIVERY-001", "배송을 찾을 수 없는 경우"),

    // PRODUCT
    PRODUCT_NOT_FOUND(404,"PRODUCT-001", "상품을 찾을 수 없는 경우"),
    PRODUCT_LIKE_NOT_FOUND(404, "PRODUCT-002", "상품 좋아요를 찾을 수 없는 경우"),
    PRODUCT_LIKE_EXISTS_ALREADY(409, "PRODUCT-003", "상품 좋아요가 이미 존재하는 경우"),

    // MAIL
    SEND_MAIL_FAIL(422, "MAIL-001", "메일 전송에 실패한 경우"),


    INTERNAL_SERVER_ERROR(500, "SERVER-001", "서버가 요청을 처리할 수 없는 경우");

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
