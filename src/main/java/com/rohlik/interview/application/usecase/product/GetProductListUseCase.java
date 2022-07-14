package com.rohlik.interview.application.usecase.product;

import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.domain.Product;

import java.util.List;

public class GetProductListUseCase {
    private final ProductRepository productRepository;

    public GetProductListUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> invoke() throws RuntimeException {
        return this.productRepository.findAll();
    }
}
