package com.rohlik.interview.context;

import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Order;
import com.rohlik.interview.domain.OrderItem;
import com.rohlik.interview.domain.OrderStatus;
import com.rohlik.interview.domain.Product;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.mock;

public class SharedContext {
    public final static long NON_EXISTENT_PRODUCT_ID = 10L;
    public final static Product PRODUCT1 = new Product(1L, "Apple", 10, 100);
    public final static Product PRODUCT2 = new Product(2L, "Bread", 20, 200);
    public final static Product PRODUCT3 = new Product(3L, "Cookies", 30, 300);
    public final static Product PRODUCT4 = new Product(4L, "Detergent", 40, 400);
    public final static Product INPUT_PRODUCT = new Product("Eggplant", 50, 500);
    public final static Product INVALID_INPUT_PRODUCT = new Product(null, 0, 0);
    public final static Product CREATED_PRODUCT = new Product(5L, "Apple", 10, 100);

    public final static long NON_EXISTENT_ORDER_ID = 5L;
    public final static Order ORDER1 = new Order(1L, OrderStatus.CREATED.name(), Instant.parse("2022-01-01T00:00:00.000Z"), Collections.singleton(new OrderItem(PRODUCT1, 5)));
    public final static Order INPUT_ORDER = new Order(null, null, Collections.singleton(new OrderItem(2L, null, null, 10)));
    public final static Order INPUT_ORDER_NON_EXISTENT_PRODUCT = new Order(null, null, Collections.singleton(new OrderItem(NON_EXISTENT_PRODUCT_ID, null, null, 10)));
    public final static Order INPUT_ORDER_NO_STOCK = new Order(null, null, Collections.singleton(new OrderItem(2L, null, null, 100)));
    public final static Order CREATED_ORDER = new Order(2L, OrderStatus.CREATED.name(), Instant.parse("2022-01-01T00:00:00.000Z"), Collections.singleton(new OrderItem(PRODUCT2, 10)));




    public final static Order NON_CREATED_ORDER = new Order(OrderStatus.NOT_CREATED.name(), null, new HashSet<>());





    public final static Order EXPIRED_ORDER = new Order(1L, OrderStatus.CREATED.name(), Instant.parse("2022-01-01T00:00:00.000Z"), Collections.singleton(new OrderItem(PRODUCT1, 5)));
    public final static Order ORDER_TO_PAY = new Order(1L, OrderStatus.CREATED.name(), Instant.parse("2022-01-01T00:00:00.000Z"), Collections.singleton(new OrderItem(PRODUCT1, 5)));
    public final static Order PAID_ORDER = new Order(1L, OrderStatus.PAID.name(), Instant.parse("2022-01-01T00:00:00.000Z"), Collections.singleton(new OrderItem(PRODUCT1, 5)));
    public final static Order ORDER_TO_CANCEL = new Order(1L, OrderStatus.CREATED.name(), Instant.parse("2022-01-01T00:00:00.000Z"), Collections.singleton(new OrderItem(PRODUCT1, 5)));
    public final static Order CANCELLED_ORDER = new Order(1L, OrderStatus.CANCELLED.name(), Instant.parse("2022-01-01T00:00:00.000Z"), Collections.singleton(new OrderItem(PRODUCT1, 5)));
    public final static int ORDER_LIFETIME_SECONDS = 10;

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private Long productId;
    private Product outputProduct;
    private Product inputProduct;
    private List<Product> productList;
    private RuntimeException runtimeException;

    private Long orderId;
    private Order outputOrder;
    private Order inputOrder;
    private List<Order> orderList;
    private OrderStatus inputOrderStatus;
    private int orderLifetimeSeconds;

    public SharedContext() {
        this.productRepository = mock(ProductRepository.class);
        this.orderRepository = mock(OrderRepository.class);

        NON_CREATED_ORDER.addOrderItem(new OrderItem(PRODUCT2, 80));

    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }



    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Product getOutputProduct() {
        return outputProduct;
    }

    public void setOutputProduct(Product outputProduct) {
        this.outputProduct = outputProduct;
    }

    public Product getInputProduct() {
        return this.inputProduct;
    }

    public void setInputProduct(Product inputProduct) {
        this.inputProduct = inputProduct;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }



    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Order getOutputOrder() {
        return outputOrder;
    }

    public void setOutputOrder(Order outputOrder) {
        this.outputOrder = outputOrder;
    }

    public Order getInputOrder() {
        return inputOrder;
    }

    public void setInputOrder(Order inputOrder) {
        this.inputOrder = inputOrder;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public OrderStatus getInputOrderStatus() {
        return inputOrderStatus;
    }

    public void setInputOrderStatus(OrderStatus inputOrderStatus) {
        this.inputOrderStatus = inputOrderStatus;
    }

    public int getOrderLifetimeSeconds() {
        return orderLifetimeSeconds;
    }

    public void setOrderLifetimeSeconds(int orderLifetimeSeconds) {
        this.orderLifetimeSeconds = orderLifetimeSeconds;
    }



    public RuntimeException getRuntimeException() {
        return runtimeException;
    }

    public void setRuntimeException(RuntimeException runtimeException) {
        this.runtimeException = runtimeException;
    }
}
