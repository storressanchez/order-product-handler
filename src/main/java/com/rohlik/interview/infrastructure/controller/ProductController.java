package com.rohlik.interview.infrastructure.controller;

import com.rohlik.interview.application.exception.BadRequestException;
import com.rohlik.interview.application.exception.ProductNotFoundException;
import com.rohlik.interview.application.repository.ProductRepository;
import com.rohlik.interview.application.usecase.product.CreateProductUseCase;
import com.rohlik.interview.application.usecase.product.DeleteProductUseCase;
import com.rohlik.interview.application.usecase.product.GetProductByIdUseCase;
import com.rohlik.interview.application.usecase.product.GetProductListUseCase;
import com.rohlik.interview.application.usecase.product.UpdateProductUseCase;
import com.rohlik.interview.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/product", produces = "application/json")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable long productId) {
        try {
            Product product = new GetProductByIdUseCase(productRepository).invoke(productId);
            product.add(linkTo(methodOn(ProductController.class).getProductById(productId)).withSelfRel());
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (BadRequestException badRequestException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequestException.getMessage(), badRequestException);
        } catch (ProductNotFoundException productNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, productNotFoundException.getMessage(), productNotFoundException);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Product>> getProductList() {
        List<Product> productList = new GetProductListUseCase(productRepository).invoke();
        for (Product product : productList) {
            product.add(linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel());
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product newProduct = new CreateProductUseCase(productRepository).invoke(product);
            newProduct.add(linkTo(methodOn(ProductController.class).getProductById(newProduct.getId())).withSelfRel());
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (BadRequestException badRequestException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequestException.getMessage(), badRequestException);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable long productId, @RequestBody Product product) {
        try {
            Product newProduct = new UpdateProductUseCase(productRepository).invoke(productId, product);
            newProduct.add(linkTo(methodOn(ProductController.class).getProductById(newProduct.getId())).withSelfRel());
            return new ResponseEntity<>(newProduct, HttpStatus.OK);
        } catch (BadRequestException badRequestException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequestException.getMessage(), badRequestException);
        } catch (ProductNotFoundException productNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, productNotFoundException.getMessage(), productNotFoundException);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long productId) {
        try {
            new DeleteProductUseCase(productRepository).invoke(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BadRequestException badRequestException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badRequestException.getMessage(), badRequestException);
        } catch (ProductNotFoundException productNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, productNotFoundException.getMessage(), productNotFoundException);
        }
    }
}
