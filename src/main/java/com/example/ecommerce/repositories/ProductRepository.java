package com.example.ecommerce.repositories;

import com.example.ecommerce.EcommerceApplication;
import com.example.ecommerce.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    public List<Product> findAll(String description, Integer amount) {
        return EcommerceApplication.dbProducts.stream()
                .filter(p -> description == null || (p.getDescription() != null && p.getDescription().toLowerCase().contains(description.toLowerCase())))
                .filter(p -> amount == null || (p.getAmount() != null && p.getAmount() >= amount))
                .collect(Collectors.toList());
    }

    public Optional<Product> findById(Integer id) {
        Optional<Product> result = Optional.empty();

        for (Product product : EcommerceApplication.dbProducts) {
            if (id.equals(product.getId())) {
                result = Optional.of(product);
            }
        }

        return result;
    }

    public boolean add(Product product) {

        if (this.findAll(null, null).stream().anyMatch(p -> product.getId().equals(p.getId()))) {
            return false;
        }

        return EcommerceApplication.dbProducts.add(product);
    }

    public Optional<Product> update(Product product) {
        Optional<Product> productToUpdate = this.findById(product.getId());
        productToUpdate.ifPresent(p -> EcommerceApplication.dbProducts.set(
                EcommerceApplication.dbProducts.indexOf(p), product));

        return productToUpdate.isPresent() ? Optional.of(product) : Optional.empty();
    }

    public Optional<Product> partialUpdate(Product product) {
        Optional<Product> finalProduct = Optional.empty();
        Optional<Product> productToUpdate = this.findById(product.getId());

        if (productToUpdate.isPresent()) {
            Product p = productToUpdate.get();

            if (product.getDescription() != null) {
                p.setDescription(product.getDescription());
            }

            if (product.getAmount() != null && product.getAmount() >= 0) {
                p.setAmount(product.getAmount());
            }

            finalProduct = Optional.of(p);
        }

        return finalProduct;
    }

    public boolean delete(Integer id) {
        Optional<Product> productToDelete = this.findById(id);
        productToDelete.ifPresent(p -> EcommerceApplication.dbProducts.remove(p));

        return productToDelete.isPresent();
    }
}
