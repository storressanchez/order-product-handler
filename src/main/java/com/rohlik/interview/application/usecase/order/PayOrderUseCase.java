package com.rohlik.interview.application.usecase.order;

import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.domain.Order;
import com.rohlik.interview.domain.OrderStatus;

public class PayOrderUseCase {
    private final OrderRepository orderRepository;

    public PayOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order invoke(Order order) throws RuntimeException {
        order.setStatus(OrderStatus.PAID.name());
        return orderRepository.save(order);
    }
}
