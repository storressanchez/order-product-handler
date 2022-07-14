package com.rohlik.interview.application.usecase.product;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Product;

import static java.util.Objects.isNull;

public class UpdateProductUseCase {
    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product invoke(Long productId, Product product) throws RuntimeException {
        if (isNull(productId)) {
            throw new BadRequestException("Input parameter productId was not provided");
        }
        if (isNull(product) || isNull(product.getName()) || isNull(product.getAvailableQuantity()) || isNull(product.getPrice())) {
            throw new BadRequestException("Input parameter product or any of its properties were not provided");
        }
        Product requestedProduct = this.productRepository.findById((long) productId);

        if (isNull(requestedProduct)) {
            throw new ProductNotFoundException(String.format("Unable to find product with ID %d", productId));
        }
        requestedProduct.setName(product.getName());
        requestedProduct.setAvailableQuantity(product.getAvailableQuantity());
        requestedProduct.setPrice(product.getPrice());
        return productRepository.save(requestedProduct);
    };
}
