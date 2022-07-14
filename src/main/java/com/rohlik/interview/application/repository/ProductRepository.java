package com.rohlik.interview.application.repository;

import com.rohlik.interview.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findById(long id);

    List<Product> findAll();
}
