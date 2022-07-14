package com.rohlik.interview.stepdefs.order;

import com.rohlik.interview.application.usecase.order.GetOrderListUseCase;
import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetOrderListStepdefs {
    private final SharedContext sharedContext;

    public GetOrderListStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @When("the user retrieves the order list")
    public void theUserRetrievesTheOrderList() {
        this.sharedContext.setOrderList(
                new GetOrderListUseCase(this.sharedContext.getOrderRepository())
                        .invoke());
    }

    @Then("the order list is successfully retrieved")
    public void theOrderListIsSuccessfullyRetrieved() {
        verify(this.sharedContext.getOrderRepository(), times(1)).findAll();
        assertThat(this.sharedContext.getOrderList()).usingRecursiveComparison().isEqualTo(Arrays.asList(SharedContext.ORDER1));
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }
}
