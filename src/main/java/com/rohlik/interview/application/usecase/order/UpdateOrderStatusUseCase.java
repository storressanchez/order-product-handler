package com.rohlik.interview.application.usecase.order;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.OrderNotFoundException;
import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Order;
import com.rohlik.interview.domain.OrderStatus;

import java.util.Objects;

import static java.util.Objects.isNull;

public class UpdateOrderStatusUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public UpdateOrderStatusUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order invoke(Long orderId, OrderStatus orderStatus) throws RuntimeException {
        if (isNull(orderId) || isNull(orderStatus)) {
            throw new BadRequestException("Input parameters orderId or orderStatus were not provided");
        }
        Order order = this.orderRepository.findById((long) orderId);

        if (isNull(order)) {
            throw new OrderNotFoundException(String.format("Unable to find order with ID %d", orderId));
        }
        if (!Objects.equals(order.getStatus(), OrderStatus.CREATED.name())) {
            throw new BadRequestException(String.format("It is not possible to pay or cancel an order in status %s", order.getStatus()));
        }

        switch (orderStatus) {
            case CANCELLED:
                return new CancelOrderUseCase(this.orderRepository, this.productRepository).invoke(order);
            case PAID:
                return new PayOrderUseCase(this.orderRepository).invoke(order);
            default:
                throw new BadRequestException("Input parameter orderStatus must be either CANCELLED or PAID");
        }
    }
}
