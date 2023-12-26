package com.example.ecommerce.repositories;

import com.example.ecommerce.EcommerceApplication;
import com.example.ecommerce.models.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartRepository {

    public List<Cart> findAll() {
        return EcommerceApplication.dbCarts;
    }

    public Optional<Cart> findById(Integer id) {
        Optional<Cart> result = Optional.empty();

        for (Cart cart : EcommerceApplication.dbCarts) {
            if (id.equals(cart.getId())) {
                result = Optional.of(cart);
            }
        }

        return result;
    }

    public boolean add(Cart cart) {

        if (this.findAll().stream().anyMatch(c -> cart.getId().equals(c.getId()))) {
            return false;
        }

        return EcommerceApplication.dbCarts.add(cart);
    }

    public Optional<Cart> update(Cart cart) {
        Optional<Cart> cartToUpdate = this.findById(cart.getId());
        cartToUpdate.ifPresent(p -> EcommerceApplication.dbCarts.set(
                EcommerceApplication.dbCarts.indexOf(p), cart));

        return cartToUpdate.isPresent() ? Optional.of(cart) : Optional.empty();
    }

    public Optional<Cart> partialUpdate(Cart cart) {
        Optional<Cart> finalCart = Optional.empty();
        Optional<Cart> cartToUpdate = this.findById(cart.getId());

        if (cartToUpdate.isPresent()) {
            Cart p = cartToUpdate.get();

            if (cart.getProducts() != null) {
                p.setProducts(cart.getProducts());
            }

            finalCart = Optional.of(p);
        }

        return finalCart;
    }

    public boolean delete(Integer id) {
        Optional<Cart> cartToDelete = this.findById(id);
        cartToDelete.ifPresent(p -> EcommerceApplication.dbCarts.remove(p));

        return cartToDelete.isPresent();
    }
}
