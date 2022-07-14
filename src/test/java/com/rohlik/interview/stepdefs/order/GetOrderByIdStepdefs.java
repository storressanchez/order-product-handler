package com.rohlik.interview.stepdefs.order;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.OrderNotFoundException;
import com.rohlik.interview.application.usecase.order.GetOrderByIdUseCase;
import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetOrderByIdStepdefs {
    private final SharedContext sharedContext;

    public GetOrderByIdStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @When("the user retrieves the order")
    public void theUserRetrievesTheOrder() {
        try {
            this.sharedContext.setOutputOrder(
                    new GetOrderByIdUseCase(this.sharedContext.getOrderRepository())
                            .invoke(this.sharedContext.getOrderId()));
        } catch (RuntimeException runtimeException) {
            this.sharedContext.setRuntimeException(runtimeException);
        }
    }

    @Then("the order is successfully retrieved")
    public void theOrderIsSuccessfullyRetrieved() {
        verify(this.sharedContext.getOrderRepository(), times(1)).findById((long) this.sharedContext.getOrderId());
        assertThat(this.sharedContext.getOutputOrder()).usingRecursiveComparison().isEqualTo(SharedContext.ORDER1);
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("get order by ID fails with bad request error")
    public void getOrderByIDFailsWithBadRequestError() {
        verify(this.sharedContext.getOrderRepository(), times(0)).findById(anyLong());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(BadRequestException.class);
    }

    @Then("get order by ID fails with order not found error")
    public void getOrderByIDFailsWithOrderNotFoundError() {
        verify(this.sharedContext.getOrderRepository(), times(1)).findById((long) this.sharedContext.getOrderId());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(OrderNotFoundException.class);
    }
}
