package com.rohlik.interview.stepdefs.product;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.usecase.product.CreateProductUseCase;
import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateProductStepdefs {
    private final SharedContext sharedContext;

    public CreateProductStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @When("the user creates the product")
    public void theUserCreatesTheProduct() {
        try {
            this.sharedContext.setOutputProduct(
                    new CreateProductUseCase(this.sharedContext.getProductRepository())
                            .invoke(this.sharedContext.getInputProduct()));
        } catch (RuntimeException runtimeException) {
            this.sharedContext.setRuntimeException(runtimeException);
        }
    }

    @Then("the product is successfully created")
    public void theProductIsSuccessfullyCreated() {
        verify(this.sharedContext.getProductRepository(), times(1)).save(this.sharedContext.getInputProduct());
        assertThat(this.sharedContext.getOutputProduct()).usingRecursiveComparison().isEqualTo(SharedContext.CREATED_PRODUCT);
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("create product fails with bad request error")
    public void createProductFailsWithBadRequestError() {
        verify(this.sharedContext.getProductRepository(), times(0)).save(any());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(BadRequestException.class);
    }
}
