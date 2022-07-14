package com.rohlik.interview.application.usecase.order;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Order;
import com.rohlik.interview.domain.OrderItem;
import com.rohlik.interview.domain.OrderStatus;
import com.rohlik.interview.domain.Product;

import java.time.Instant;
import java.util.HashSet;

import static java.util.Objects.isNull;

public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public CreateOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order invoke(Order order) throws RuntimeException {
        if (isNull(order)) {
            throw new BadRequestException("Input parameter order was not provided");
        }

        // Check that all products exist and their available quantities
        Order newOrder = new Order(OrderStatus.CREATED.name(), Instant.now(), new HashSet<>());
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = productRepository.findById((long) orderItem.getId());
            if (isNull(product)) {
                throw new ProductNotFoundException(String.format("Unable to find product with ID %d", orderItem.getId()));
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - orderItem.getQuantity());
            newOrder.addOrderItem(new OrderItem(product, orderItem.getQuantity()));
        }

        // If there is enough stock for all requested items, the order is created and returned
        if (newOrder.getOrderItems().stream().noneMatch(orderItem -> orderItem.getProduct().getAvailableQuantity() < 0)) {
            for (OrderItem orderItem : newOrder.getOrderItems()) {
                productRepository.save(orderItem.getProduct());
            }
            return orderRepository.save(newOrder);

        // Otherwise available products are removed from the order and the missing quantity is returned
        } else {
            newOrder.setStatus(OrderStatus.NOT_CREATED.name());
            newOrder.setCreatedAt(null);
            newOrder.getOrderItems().removeIf(orderItem -> orderItem.getProduct().getAvailableQuantity() >= 0);
            newOrder.getOrderItems().forEach(orderItem -> {
                orderItem.getProduct().setAvailableQuantity(orderItem.getProduct().getAvailableQuantity() + orderItem.getQuantity());
                orderItem.setQuantity(orderItem.getQuantity() - orderItem.getProduct().getAvailableQuantity());
            });
            return newOrder;
        }
    }
}
