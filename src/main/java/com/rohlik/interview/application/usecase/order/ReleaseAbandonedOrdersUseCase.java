package com.rohlik.interview.application.usecase.order;

import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Order;
import com.rohlik.interview.domain.OrderItem;
import com.rohlik.interview.domain.OrderStatus;
import com.rohlik.interview.domain.Product;

import java.time.Instant;
import java.util.List;

public class ReleaseAbandonedOrdersUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ReleaseAbandonedOrdersUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void invoke(int orderLifetimeSeconds) throws RuntimeException {
        List<Order> orderList = orderRepository.findWithStatusOlderThan(OrderStatus.CREATED.name(), Instant.now().minusSeconds(orderLifetimeSeconds));

        for (Order order : orderList) {
            for (OrderItem orderItem : order.getOrderItems()) {
                Product product = orderItem.getProduct();
                product.setAvailableQuantity(product.getAvailableQuantity() + orderItem.getQuantity());
                productRepository.save(product);
            }
            order.setStatus(OrderStatus.CANCELLED.name());
            orderRepository.save(order);
        }
    };
}
