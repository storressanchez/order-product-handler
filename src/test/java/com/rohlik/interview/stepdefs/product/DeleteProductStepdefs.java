package com.rohlik.interview.stepdefs.product;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.usecase.product.DeleteProductUseCase;
import com.rohlik.interview.context.SharedContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DeleteProductStepdefs {
    private final SharedContext sharedContext;

    public DeleteProductStepdefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @When("the user deletes the product")
    public void theUserDeletesTheProduct() {
        try {
            new DeleteProductUseCase(this.sharedContext.getProductRepository())
                    .invoke(this.sharedContext.getProductId());
        } catch (RuntimeException runtimeException) {
            this.sharedContext.setRuntimeException(runtimeException);
        }
    }

    @Then("the product is successfully deleted")
    public void theProductIsSuccessfullyDeleted() {
        verify(this.sharedContext.getProductRepository(), times(1)).existsById(this.sharedContext.getProductId());
        assertThat(this.sharedContext.getRuntimeException()).isNull();
    }

    @Then("delete product fails with bad request error")
    public void deleteProductFailsWithBadRequestError() {
        verify(this.sharedContext.getProductRepository(), times(0)).existsById(anyLong());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(BadRequestException.class);
    }

    @Then("delete product fails with product not found error")
    public void deleteProductFailsWithProductNotFoundError() {
        verify(this.sharedContext.getProductRepository(), times(1)).existsById(this.sharedContext.getProductId());
        assertThat(this.sharedContext.getRuntimeException()).isInstanceOf(ProductNotFoundException.class);
    }
}
