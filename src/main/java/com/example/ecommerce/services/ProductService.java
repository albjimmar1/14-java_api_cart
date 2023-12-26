package com.example.ecommerce.services;

import com.example.ecommerce.builders.ResponseBuilder;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ResponseBuilder findAll(String description, Integer amount) {
        List<Product> results = this.productRepository.findAll(description, amount);

        return new ResponseBuilder(
                results,
                HttpStatus.OK
        );
    }

    public ResponseBuilder findById(Integer id) {
        Optional<Product> result = this.productRepository.findById(id);

        return new ResponseBuilder(
                result.orElse(null),
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder add(Product product) {
        List<String> errors = new ArrayList<>();

        if (product.getId() == null)
            errors.add("Product id is required");
        if (product.getDescription() == null)
            errors.add("Product description is required");
        if (product.getAmount() == null)
            errors.add("Product amount is required");

        if (!errors.isEmpty()) {
            return new ResponseBuilder(
                    product,
                    HttpStatus.BAD_REQUEST
            );
        }

        if (this.productRepository.add(product)) {
            return new ResponseBuilder(product, HttpStatus.CREATED);
        } else {
            return new ResponseBuilder(product, HttpStatus.CONFLICT);
        }
    }

    public ResponseBuilder update(Product product, Integer urlId) {
        List<String> errors = new ArrayList<>();

        if (product.getId() == null)
            errors.add("Product id is required");
        if (!Objects.equals(product.getId(), urlId))
            errors.add("Product id and url id must be the same");

        if (!errors.isEmpty()) {
            return new ResponseBuilder(product, HttpStatus.BAD_REQUEST);
        }

        Optional<Product> result = this.productRepository.update(product);

        return new ResponseBuilder(
                result.orElse(product),
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder partialUpdate(Product product, Integer urlId) {

        List<String> errors = new ArrayList<>();

        if (product.getId() == null)
            errors.add("Product id is required");
        if (!Objects.equals(product.getId(), urlId))
            errors.add("Product id and url id must be the same");

        if (!errors.isEmpty()) {
            return new ResponseBuilder(product, HttpStatus.BAD_REQUEST);
        }

        Optional<Product> result = this.productRepository.partialUpdate(product);

        return new ResponseBuilder(
                result.orElse(product),
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder delete(Integer id) {
        boolean result = this.productRepository.delete(id);

        return new ResponseBuilder(
                result ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

}
