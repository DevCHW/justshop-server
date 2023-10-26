package com.justshop.core.kafka.message;

public interface Topics {
    String MEMBER_CREATE = "member-create";
    String ORDER_CREATE = "order-create";
    String POINT_EVENT_CREATE = "point-event-create";
    String ORDER_STATUS_UPDATE = "order-status-update";
    String ORDER_FAIL = "order-fail";
    String ORDER_SUCCESS = "order-success";
}
