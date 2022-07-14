package com.rohlik.interview.infrastructure.controller;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.OrderNotFoundException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.repository.OrderRepository;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.application.usecase.order.CreateOrderUseCase;
import com.rohlik.interview.application.usecase.order.GetOrderByIdUseCase;
import com.rohlik.interview.application.usecase.order.GetOrderListUseCase;
import com.rohlik.interview.application.usecase.order.UpdateOrderStatusUseCase;
import com.rohlik.interview.domain.Order;
import com.rohlik.interview.domain.OrderItem;
import com.rohlik.interview.domain.OrderStatus;
import com.rohlik.interview.infrastructure.request.UpdateOrderStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/order", produces = "application/json")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable long orderId) {
        try {
            Order order = new GetOrderByIdUseCase(orderRepository).invoke(orderId);
            order.add(linkTo(methodOn(OrderController.class).getOrderById(orderId)).withSelfRel());
            for (OrderItem orderItem : order.getOrderItems()) {
                if (orderItem.getProduct().getLinks().isEmpty()) {
                    orderItem.getProduct().add(linkTo(methodOn(ProductController.class).getProductById(orderItem.getProduct().getId())).withSelfRel());
                }
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (BadRequestException badRequestException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequestException.getMessage(), badRequestException);
        } catch (OrderNotFoundException orderNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, orderNotFoundException.getMessage(), orderNotFoundException);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Order>> getOrderList() {
        List<Order> orderList = new GetOrderListUseCase(orderRepository).invoke();
        for (Order order : orderList) {
            order.add(linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
            for (OrderItem orderItem : order.getOrderItems()) {
                if (orderItem.getProduct().getLinks().isEmpty()) {
                    orderItem.getProduct().add(linkTo(methodOn(ProductController.class).getProductById(orderItem.getProduct().getId())).withSelfRel());
                }
            }
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order newOrder = new CreateOrderUseCase(orderRepository, productRepository).invoke(order);
            if (Objects.equals(newOrder.getStatus(), OrderStatus.CREATED.name())) {
                newOrder.add(linkTo(methodOn(OrderController.class).getOrderById(newOrder.getId())).withSelfRel());
                for (OrderItem orderItem : newOrder.getOrderItems()) {
                    if (orderItem.getProduct().getLinks().isEmpty()) {
                        orderItem.getProduct().add(linkTo(methodOn(ProductController.class).getProductById(orderItem.getProduct().getId())).withSelfRel());
                    }
                }
                return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(newOrder, HttpStatus.BAD_REQUEST);
            }
        } catch (BadRequestException badRequestException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequestException.getMessage(), badRequestException);
        } catch (ProductNotFoundException productNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, productNotFoundException.getMessage(), productNotFoundException);
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable long orderId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        try {
            Order newOrder = new UpdateOrderStatusUseCase(orderRepository, productRepository).invoke(orderId, updateOrderStatusRequest.getOrderStatus());
            newOrder.add(linkTo(methodOn(ProductController.class).getProductById(newOrder.getId())).withSelfRel());
            return new ResponseEntity<>(newOrder, HttpStatus.OK);
        } catch (BadRequestException badRequestException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequestException.getMessage(), badRequestException);
        } catch (OrderNotFoundException orderNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, orderNotFoundException.getMessage(), orderNotFoundException);
        }
    }
}
