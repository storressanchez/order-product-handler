package com.rohlik.interview.stepdefs.order;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.OrderNotFoundException;
import com.rohlik.interview.application.usecase.order.UpdateOrderStatusUseCase;
import com.rohlik.interview.context.SharedContext;
import com.rohlik.interview.domain.OrderStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UpdateOrderStatusStepdefs {
    private final SharedContext sharedContext;

    public UpdateOrderStatusStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @Given("a valid status to pay the order")
    public void aValidStatusToPayTheOrder() {
        this.sharedContext.setInputOrderStatus(OrderStatus.PAID);
    }

    @Given("a valid status to cancel the order")
    public void aValidStatusToCancelTheOrder() {
        this.sharedContext.setInputOrderStatus(OrderStatus.CANCELLED);
    }

    @Given("an invalid status")
    public void anInvalidStatus() {
        this.sharedContext.setInputOrderStatus(null);
    }

    @Given("a non accepted status")
    public void aNonAcceptedStatus() {
        this.sharedContext.setInputOrderStatus(OrderStatus.CREATED);
    }

    @When("the user updates the order")
    public void theUserUpdatesTheOrder() {
        try {
            this.sharedContext.setOutputOrder(
                    new UpdateOrderStatusUseCase(this.sharedContext.getOrderRepository(), this.sharedContext.getProductRepository())
                            .invoke(this.sharedContext.getOrderId(), this.sharedContext.getInputOrderStatus()));
        } catch (RuntimeException runtimeException) {
            this.sharedContext.setRuntimeException(runtimeException);
        }
    }

    @Then("the order status is successfully updated to paid")
    public void theOrderStatusIsSuccessfullyUpdatedToPaid() {
        verify(this.sharedContext.getOrderRepository(), times(1)).findById((long) this.sharedContext.getOrderId());
        verify(this.sharedContext.getOrderRepository(), times(1)).save(SharedContext.PAID_ORDER);
        assertThat(this.sharedContext.getOutputOrder()).usingRecursiveComparison().isEqualTo(SharedContext.PAID_ORDER);
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("the order status is successfully updated to cancelled")
    public void theOrderStatusIsSuccessfullyUpdatedToCancelled() {
        verify(this.sharedContext.getOrderRepository(), times(1)).findById((long) this.sharedContext.getOrderId());
        verify(this.sharedContext.getProductRepository(), times(1)).save(any());
        assertThat(this.sharedContext.getOutputOrder()).usingRecursiveComparison().isEqualTo(SharedContext.CANCELLED_ORDER);
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("update order status fails with bad request error")
    public void updateOrderStatusFailsWithBadRequestError() {
        verify(this.sharedContext.getOrderRepository(), times(0)).findById(anyLong());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(BadRequestException.class);
    }

    @Then("update order status fails with order not found error")
    public void updateOrderStatusFailsWithOrderNotFoundError() {
        verify(this.sharedContext.getOrderRepository(), times(1)).findById((long) this.sharedContext.getOrderId());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(OrderNotFoundException.class);
    }

    @Then("update order status finds the order but fails with bad request error")
    public void updateOrderStatusFindsTheOrderButFailsWithBadRequestError() {
        verify(this.sharedContext.getOrderRepository(), times(1)).findById((long) this.sharedContext.getOrderId());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(BadRequestException.class);
    }
}
