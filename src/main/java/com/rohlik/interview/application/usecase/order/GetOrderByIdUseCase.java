package com.rohlik.interview.application.usecase.order;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.OrderNotFoundException;
import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.domain.Order;

import static java.util.Objects.isNull;

public class GetOrderByIdUseCase {
    private final OrderRepository orderRepository;

    public GetOrderByIdUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order invoke(Long orderId) throws RuntimeException {
        if (isNull(orderId)) {
            throw new BadRequestException("Input parameter orderId was not provided");
        }
        Order requestedOrder = this.orderRepository.findById((long) orderId);

        if (isNull(requestedOrder)) {
            throw new OrderNotFoundException(String.format("Unable to find order with ID %d", orderId));
        }
        return requestedOrder;
    }
}
