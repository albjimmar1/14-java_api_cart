package com.example.ecommerce.controllers;

import com.example.ecommerce.builders.ResponseBuilder;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/v1")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity<Object> getCarts() {
        ResponseBuilder response = this.cartService.findAll();

        return new ResponseEntity<>(
                response,
                response.getStatus()
        );
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<Object> getCartById(@PathVariable Integer id) {
        ResponseBuilder response = this.cartService.findById(id);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/carts")
    public ResponseEntity<Object> addCart(@RequestBody Cart cart) {
        ResponseBuilder response = this.cartService.add(cart);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/carts/{id}")
    public ResponseEntity<Object> updateCart(@RequestBody Cart cart, @PathVariable Integer id) {
        ResponseBuilder response = this.cartService.update(cart, id);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/carts/{id}")
    public ResponseEntity<Object> partialUpdateCart(@RequestBody Cart cart, @PathVariable Integer id) {
        ResponseBuilder response = this.cartService.partialUpdate(cart, id);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Object> deleteCart(@PathVariable Integer id) {
        ResponseBuilder response = this.cartService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/carts/{id}/products")
    public ResponseEntity<Object> getAllProductsFromCart(@PathVariable Integer id) {
        ResponseBuilder response = this.cartService.findAllProducts(id);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/carts/{idCart}/products/{idProduct}")
    public ResponseEntity<Object> getProductByIdFromCart(@PathVariable Integer idCart, @PathVariable Integer idProduct) {
        ResponseBuilder response = this.cartService.findProductById(idCart, idProduct);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/carts/{idCart}/products")
    public ResponseEntity<Object> addProductsToCart(@PathVariable Integer idCart, @RequestBody List<Product> products) {
        ResponseBuilder response = this.cartService.addProduct(idCart, products);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/carts/{idCart}/products/{idProduct}")
    public ResponseEntity<Object> removeProductToCart(@PathVariable Integer idCart, @PathVariable Integer idProduct, @RequestBody Product product) {
        ResponseBuilder response = this.cartService.removeProduct(idCart, idProduct, product);

        return new ResponseEntity<>(response, response.getStatus());
    }
}
