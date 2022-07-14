package com.rohlik.interview.application.usecase.product;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Product;

import static java.util.Objects.isNull;

public class GetProductByIdUseCase {
    private final ProductRepository productRepository;

    public GetProductByIdUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product invoke(Long productId) throws RuntimeException {
        if (isNull(productId)) {
            throw new BadRequestException("Input parameter productId was not provided");
        }
        Product requestedProduct = this.productRepository.findById((long) productId);

        if (isNull(requestedProduct)) {
            throw new ProductNotFoundException(String.format("Unable to find product with ID %d", productId));
        }
        return requestedProduct;
    }
}
