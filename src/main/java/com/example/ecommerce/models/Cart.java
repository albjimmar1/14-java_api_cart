package com.example.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Cart {

    private Integer id;

    private List<Product> products;

    private Date lastUpdate;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public Cart(Integer id, List<Product> products) {
        this.id = id;
        this.products = products != null ? products : new ArrayList<>();
    }

    public Cart(Cart cart) {
        this.id = cart.id;
        this.products = new ArrayList<>(cart.products);
        this.lastUpdate = cart.lastUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
