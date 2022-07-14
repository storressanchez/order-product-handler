package com.rohlik.interview.infrastructure.scheduler;

import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.application.usecase.order.ReleaseAbandonedOrdersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderScheduler {
    private static final int INITIAL_DELAY_MILLISECONDS = 1800000;
    private static final int FIXED_RATE_MILLISECONDS = 120000;
    private static final int ORDER_LIFETIME_SECONDS = 1800;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Scheduled(initialDelay = INITIAL_DELAY_MILLISECONDS, fixedRate = FIXED_RATE_MILLISECONDS)
    public void CancelAbandonedOrders() {
        new ReleaseAbandonedOrdersUseCase(orderRepository, productRepository).invoke(ORDER_LIFETIME_SECONDS);
    }
}
