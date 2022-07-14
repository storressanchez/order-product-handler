package com.rohlik.interview.stepdefs.order;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.usecase.order.CreateOrderUseCase;
import com.rohlik.interview.context.SharedContext;
import com.rohlik.interview.domain.Order;
import com.rohlik.interview.domain.OrderItem;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateOrderStepdefs {
    private final SharedContext sharedContext;

    public CreateOrderStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @Given("a valid input order")
    public void aValidInputOrder() {
        this.sharedContext.setInputOrder(SharedContext.INPUT_ORDER);
    }

    @Given("an invalid input order")
    public void anInvalidInputOrder() {
        this.sharedContext.setInputOrder(null);
    }

    @Given("an order with a non existent product ID")
    public void anOrderWithANonExistentProductID() {
        this.sharedContext.setInputOrder(SharedContext.INPUT_ORDER_NON_EXISTENT_PRODUCT);
    }

    @Given("an order for which there is not enough product stock")
    public void anOrderForWhichThereIsNotEnoughProductStock() {
        this.sharedContext.setInputOrder(SharedContext.INPUT_ORDER_NO_STOCK);
    }

    @When("the user creates the order")
    public void theUserCreatesTheOrder() {
        try {
            this.sharedContext.setOutputOrder(
                    new CreateOrderUseCase(this.sharedContext.getOrderRepository(), this.sharedContext.getProductRepository())
                            .invoke(this.sharedContext.getInputOrder()));
        } catch (RuntimeException runtimeException) {
            this.sharedContext.setRuntimeException(runtimeException);
        }
    }

    @Then("create order returns the list of missing products")
    public void createOrderReturnsTheListOfMissingProducts() {
        for (OrderItem orderItem : sharedContext.getInputOrder().getOrderItems()) {
            verify(this.sharedContext.getProductRepository(), times(1)).findById((long) orderItem.getId());
        }
        verify(this.sharedContext.getOrderRepository(), times(0)).save(any());
        assertThat(this.sharedContext.getOutputOrder()).usingRecursiveComparison().isEqualTo(SharedContext.NON_CREATED_ORDER);
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("the order is successfully created")
    public void theOrderIsSuccessfullyCreated() {
        for (OrderItem orderItem : sharedContext.getInputOrder().getOrderItems()) {
            verify(this.sharedContext.getProductRepository(), times(1)).findById((long) orderItem.getId());
            verify(this.sharedContext.getProductRepository(), times(1)).save(any());
        }
        verify(this.sharedContext.getOrderRepository(), times(1)).save(any());
        assertThat(this.sharedContext.getOutputOrder()).usingRecursiveComparison().isEqualTo(SharedContext.CREATED_ORDER);
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("create order fails with bad request error")
    public void createOrderFailsWithBadRequestError() {
        verify(this.sharedContext.getProductRepository(), times(0)).findById(anyLong());
        verify(this.sharedContext.getProductRepository(), times(0)).save(any());
        verify(this.sharedContext.getOrderRepository(), times(0)).save(any());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(BadRequestException.class);
    }

    @Then("create order fails with product not found error")
    public void createOrderFailsWithProductNotFoundError() {
        for (OrderItem orderItem : sharedContext.getInputOrder().getOrderItems()) {
            verify(this.sharedContext.getProductRepository(), times(1)).findById((long) orderItem.getId());
        }
        verify(this.sharedContext.getProductRepository(), times(0)).save(any());
        verify(this.sharedContext.getOrderRepository(), times(0)).save(any());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(ProductNotFoundException.class);
    }
}
