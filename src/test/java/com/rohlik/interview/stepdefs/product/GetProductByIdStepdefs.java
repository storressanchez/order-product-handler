package com.rohlik.interview.stepdefs.product;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.usecase.product.GetProductByIdUseCase;
import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetProductByIdStepdefs {
    private final SharedContext sharedContext;

    public GetProductByIdStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @When("the user retrieves the product")
    public void theUserRetrievesTheProduct() {
        try {
            this.sharedContext.setOutputProduct(
                    new GetProductByIdUseCase(this.sharedContext.getProductRepository())
                            .invoke(this.sharedContext.getProductId()));
        } catch (RuntimeException runtimeException) {
            this.sharedContext.setRuntimeException(runtimeException);
        }
    }

    @Then("the product is successfully retrieved")
    public void theProductIsSuccessfullyRetrieved() {
        verify(this.sharedContext.getProductRepository(), times(1)).findById((long) this.sharedContext.getProductId());
        assertThat(this.sharedContext.getOutputProduct()).usingRecursiveComparison().isEqualTo(SharedContext.PRODUCT1);
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("get product by ID fails with bad request error")
    public void getProductByIDFailsWithBadRequestError() {
        verify(this.sharedContext.getProductRepository(), times(0)).findById(anyLong());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(BadRequestException.class);
    }

    @Then("get product by ID fails with product not found error")
    public void getProductByIDFailsWithProductNotFoundError() {
        verify(this.sharedContext.getProductRepository(), times(1)).findById((long) this.sharedContext.getProductId());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(ProductNotFoundException.class);
    }
}
