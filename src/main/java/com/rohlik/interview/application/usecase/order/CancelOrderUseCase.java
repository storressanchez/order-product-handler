package com.rohlik.interview.application.usecase.order;

import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Order;
import com.rohlik.interview.domain.OrderItem;
import com.rohlik.interview.domain.OrderStatus;
import com.rohlik.interview.domain.Product;

public class CancelOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public CancelOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order invoke(Order order) throws RuntimeException {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.setAvailableQuantity(product.getAvailableQuantity() + orderItem.getQuantity());
            productRepository.save(product);
        }
        order.setStatus(OrderStatus.CANCELLED.name());
        return orderRepository.save(order);
    }
}
