package com.example.ecommerce.controllers;

import com.example.ecommerce.builders.ResponseBuilder;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/v1")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Object> getProducts(
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "amount", required = false) Integer amount
    ) {
        ResponseBuilder response = this.productService.findAll(description, amount);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Integer id) {
        ResponseBuilder response = this.productService.findById(id);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("products")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        ResponseBuilder response = this.productService.add(product);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable Integer id) {
        ResponseBuilder response = this.productService.update(product, id);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Object> partialUpdateProduct(@RequestBody Product product, @PathVariable Integer id) {
        ResponseBuilder response = this.productService.partialUpdate(product, id);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Integer id) {
        ResponseBuilder response = this.productService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
