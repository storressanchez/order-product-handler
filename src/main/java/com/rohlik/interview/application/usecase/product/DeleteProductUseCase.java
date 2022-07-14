package com.rohlik.interview.application.usecase.product;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.repository.ProductRepository;

import static java.util.Objects.isNull;

public class DeleteProductUseCase {
    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void invoke(Long productId) throws RuntimeException {
        if (isNull(productId)) {
            throw new BadRequestException("Input parameter productId was not provided");
        }
        boolean productExists = this.productRepository.existsById(productId);

        if (!productExists) {
            throw new ProductNotFoundException(String.format("Unable to find product with ID %d", productId));
        }
        productRepository.deleteById(productId);
    }
}
