package com.rohlik.interview.domain;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends RepresentationModel<Product> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer availableQuantity;
    private Integer price;

    public Product() {
    }

    public Product(String name, Integer availableQuantity, Integer price) {
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public Product(Long id, String name, Integer availableQuantity, Integer price) {
        this.id = id;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailableQuantity() {
        return this.availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
