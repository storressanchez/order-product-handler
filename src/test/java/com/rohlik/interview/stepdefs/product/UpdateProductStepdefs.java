package com.rohlik.interview.stepdefs.product;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.usecase.product.UpdateProductUseCase;
import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UpdateProductStepdefs {
    private final SharedContext sharedContext;

    public UpdateProductStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @When("the user updates the product")
    public void theUserUpdatesTheProduct() {
        try {
            this.sharedContext.setOutputProduct(
                    new UpdateProductUseCase(this.sharedContext.getProductRepository())
                            .invoke(this.sharedContext.getProductId(), this.sharedContext.getInputProduct()));
        } catch (RuntimeException runtimeException) {
            this.sharedContext.setRuntimeException(runtimeException);
        }
    }

    @Then("the product is successfully updated")
    public void theProductIsSuccessfullyUpdated() {
        verify(this.sharedContext.getProductRepository(), times(1)).findById((long) this.sharedContext.getProductId());
        verify(this.sharedContext.getProductRepository(), times(1)).save(this.sharedContext.getInputProduct());
        assertThat(this.sharedContext.getOutputProduct()).usingRecursiveComparison().isEqualTo(SharedContext.CREATED_PRODUCT);
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("update product fails with bad request error")
    public void updateProductFailsWithBadRequestError() {
        verify(this.sharedContext.getProductRepository(), times(0)).findById(anyLong());
        verify(this.sharedContext.getProductRepository(), times(0)).save(any());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(BadRequestException.class);
    }

    @Then("update product fails with product not found error")
    public void updateProductFailsWithProductNotFoundError() {
        verify(this.sharedContext.getProductRepository(), times(1)).findById((long) this.sharedContext.getProductId());
        verify(this.sharedContext.getProductRepository(), times(0)).save(any());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(ProductNotFoundException.class);
    }
}
