package com.rohlik.interview.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends RepresentationModel<Order> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Instant createdAt;
    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems;

    public Order() {
    }

    public Order(String status, Instant createdAt, Set<OrderItem> orderItems) {
        this.status = status;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    public Order(Long id, String status, Instant createdAt, Set<OrderItem> orderItems) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    public OrderItem addOrderItem(OrderItem orderItem) {
        getOrderItems().add(orderItem);
        orderItem.setOrder(this);
        return orderItem;
    }

    public OrderItem removeOrderItem(OrderItem orderItem) {
        getOrderItems().remove(orderItem);
        orderItem.setOrder(null);
        return orderItem;
    }

    public void pay() {
        setStatus(OrderStatus.PAID.name());
    }

    public void cancel() {
        setStatus(OrderStatus.CANCELLED.name());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getTotalCost() {
        int accumulator = 0;
        for (OrderItem orderItem : this.orderItems) {
            accumulator += orderItem.getQuantity() * orderItem.getProduct().getPrice();
        }
        return accumulator;
    }
}