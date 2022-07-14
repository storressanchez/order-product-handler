package com.rohlik.interview.infrastructure.scheduler;

import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.application.usecase.product.CreateProductUseCase;
import com.rohlik.interview.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProductScheduler {
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void populateDatabase() {
        CreateProductUseCase createProductUseCase = new CreateProductUseCase(productRepository);
        createProductUseCase.invoke(new Product("Apple", 10, 100));
        createProductUseCase.invoke(new Product("Bread", 20, 200));
        createProductUseCase.invoke(new Product("Cookies", 30, 300));
        createProductUseCase.invoke(new Product("Detergent", 40, 400));
        createProductUseCase.invoke(new Product("Eggplant", 50, 500));
    }
}
