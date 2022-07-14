package com.rohlik.interview.stepdefs.order;

import com.rohlik.interview.application.usecase.order.ReleaseAbandonedOrdersUseCase;
import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReleaseAbandonedOrdersStepdefs {
    private final SharedContext sharedContext;

    public ReleaseAbandonedOrdersStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @Given("a lifetime expiration time")
    public void aLifetimeExpirationTime() {
        this.sharedContext.setOrderLifetimeSeconds(SharedContext.ORDER_LIFETIME_SECONDS);
    }

    @When("the user cancels abandoned orders")
    public void theUserCancelsAbandonedOrders() {
        new ReleaseAbandonedOrdersUseCase(this.sharedContext.getOrderRepository(), this.sharedContext.getProductRepository()).invoke(this.sharedContext.getOrderLifetimeSeconds());
    }

    @Then("the abandoned orders are cancelled")
    public void theAbandonedOrdersAreCancelled() {
        verify(this.sharedContext.getOrderRepository(), times(1)).findWithStatusOlderThan(any(), any());
        verify(this.sharedContext.getProductRepository(), times(1)).save(any());
        verify(this.sharedContext.getOrderRepository(), times(1)).save(any());
    }
}
