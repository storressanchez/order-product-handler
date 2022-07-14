package com.rohlik.interview.infrastructure.request;

import com.rohlik.interview.domain.OrderStatus;

public class UpdateOrderStatusRequest {
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
