package com.rohlik.interview.application.usecase.order;

import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.domain.Order;

import java.util.List;

public class GetOrderListUseCase {
    private final OrderRepository orderRepository;

    public GetOrderListUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> invoke() throws RuntimeException {
        return this.orderRepository.findAll();
    }
}
