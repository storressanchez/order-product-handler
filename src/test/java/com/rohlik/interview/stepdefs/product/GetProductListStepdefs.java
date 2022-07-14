package com.rohlik.interview.stepdefs.product;

import com.rohlik.interview.application.usecase.product.GetProductListUseCase;
import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetProductListStepdefs {
    private final SharedContext sharedContext;

    public GetProductListStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @When("the user retrieves the product list")
    public void theUserRetrievesTheProductList() {
        this.sharedContext.setProductList(
                new GetProductListUseCase(this.sharedContext.getProductRepository())
                        .invoke());
    }

    @Then("the product list is successfully retrieved")
    public void theProductListIsSuccessfullyRetrieved() {
        verify(this.sharedContext.getProductRepository(), times(1)).findAll();
        assertThat(this.sharedContext.getProductList()).usingRecursiveComparison().isEqualTo(Arrays.asList(SharedContext.PRODUCT1, SharedContext.PRODUCT2, SharedContext.PRODUCT3, SharedContext.PRODUCT4));
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }
}
