package com.example.ecommerce.models;

public class Product {

    private Integer id;

    private String description;

    private Integer amount;

    public Product() {
    }

    public Product(Integer id, String description, Integer amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
