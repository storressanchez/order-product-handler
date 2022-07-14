package com.rohlik.interview.application.usecase.product;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Product;

import static java.util.Objects.isNull;

public class CreateProductUseCase {
    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product invoke(Product product) throws RuntimeException {
        if (isNull(product) || isNull(product.getName()) || isNull(product.getAvailableQuantity()) || isNull(product.getPrice())) {
            throw new BadRequestException("Input parameter product or any of its properties were not provided");
        }
        return productRepository.save(product);
    }
}
