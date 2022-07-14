package com.rohlik.interview.stepdefs;

import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Given;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SharedStepdefs {
    private final SharedContext sharedContext;

    public SharedStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @Given("a valid product ID")
    public void aValidProductId() {
        this.sharedContext.setProductId(SharedContext.PRODUCT1.getId());
    }

    @Given("an invalid product ID")
    public void anInvalidProductId() {
        this.sharedContext.setProductId(null);
    }

    @Given("a non existent product ID")
    public void aNonExistentProductId() {
        this.sharedContext.setProductId(SharedContext.NON_EXISTENT_PRODUCT_ID);
    }

    @Given("a valid input product")
    public void aValidInputProduct() {
        this.sharedContext.setInputProduct(SharedContext.INPUT_PRODUCT);
    }

    @Given("an invalid input product")
    public void anInvalidInputProduct() {
        this.sharedContext.setInputProduct(null);
    }

    @Given("an input product with invalid properties")
    public void anInputProductWithInvalidProperties() {
        this.sharedContext.setInputProduct(SharedContext.INVALID_INPUT_PRODUCT);
    }

    @Given("a product repository with products")
    public void aProductRepositoryWithProducts() {
        when(this.sharedContext.getProductRepository().findById(anyLong())).thenReturn(null);
        when(this.sharedContext.getProductRepository().findById((long) SharedContext.PRODUCT1.getId())).thenReturn(SharedContext.PRODUCT1);
        when(this.sharedContext.getProductRepository().findById((long) SharedContext.PRODUCT2.getId())).thenReturn(SharedContext.PRODUCT2);
        when(this.sharedContext.getProductRepository().findById((long) SharedContext.PRODUCT3.getId())).thenReturn(SharedContext.PRODUCT3);
        when(this.sharedContext.getProductRepository().findById((long) SharedContext.PRODUCT4.getId())).thenReturn(SharedContext.PRODUCT4);

        when(this.sharedContext.getProductRepository().findAll()).thenReturn(Arrays.asList(SharedContext.PRODUCT1, SharedContext.PRODUCT2, SharedContext.PRODUCT3, SharedContext.PRODUCT4));

        when(this.sharedContext.getProductRepository().save(SharedContext.INPUT_PRODUCT)).thenReturn(SharedContext.CREATED_PRODUCT);

        when(this.sharedContext.getProductRepository().existsById(SharedContext.PRODUCT1.getId())).thenReturn(true);
    }

    @Given("a valid order ID")
    public void aValidOrderId() {
        this.sharedContext.setOrderId(SharedContext.ORDER1.getId());
    }

    @Given("an invalid order ID")
    public void anInvalidOrderId() {
        this.sharedContext.setOrderId(null);
    }

    @Given("a non existent order ID")
    public void aNonExistentOrderId() {
        this.sharedContext.setOrderId(SharedContext.NON_EXISTENT_ORDER_ID);
    }

    @Given("an order repository with orders")
    public void anOrderRepositoryWithOrders() {
        when(this.sharedContext.getOrderRepository().findById(anyLong())).thenReturn(null);
        when(this.sharedContext.getOrderRepository().findById((long) SharedContext.ORDER1.getId())).thenReturn(SharedContext.ORDER1);

        when(this.sharedContext.getOrderRepository().findAll()).thenReturn(Collections.singletonList(SharedContext.ORDER1));

        when(this.sharedContext.getOrderRepository().save(SharedContext.INPUT_ORDER)).thenReturn(SharedContext.CREATED_ORDER);

        when(this.sharedContext.getOrderRepository().findWithStatusOlderThan(any(), any())).thenReturn(Collections.singletonList(SharedContext.EXPIRED_ORDER));
    }

    @Given("an order repository with an order to pay")
    public void anOrderRepositoryWithAnOrderToPay() {
        when(this.sharedContext.getOrderRepository().findById(anyLong())).thenReturn(null);
        when(this.sharedContext.getOrderRepository().findById((long) SharedContext.ORDER1.getId())).thenReturn(SharedContext.ORDER_TO_PAY);

        when(this.sharedContext.getOrderRepository().save(SharedContext.PAID_ORDER)).thenReturn(SharedContext.PAID_ORDER);
    }

    @Given("an order repository with an order to cancel")
    public void anOrderRepositoryWithAnOrderToCancel() {
        when(this.sharedContext.getOrderRepository().findById(anyLong())).thenReturn(null);
        when(this.sharedContext.getOrderRepository().findById((long) SharedContext.ORDER1.getId())).thenReturn(SharedContext.ORDER_TO_CANCEL);

        when(this.sharedContext.getOrderRepository().save(SharedContext.CANCELLED_ORDER)).thenReturn(SharedContext.CANCELLED_ORDER);
    }
}
