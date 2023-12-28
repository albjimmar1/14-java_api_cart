package com.example.ecommerce.repositories;

import com.example.ecommerce.EcommerceApplication;
import com.example.ecommerce.models.Cart;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class CartRepository {

    public List<Cart> findAll() {
        deleteLastRecordsJob();

        return EcommerceApplication.dbCarts;
    }

    public Optional<Cart> findById(Integer id) {
        deleteLastRecordsJob();

        Optional<Cart> result = Optional.empty();

        for (Cart cart : EcommerceApplication.dbCarts) {
            if (id.equals(cart.getId())) {
                result = Optional.of(cart);
            }
        }

        return result;
    }

    public boolean add(Cart cart) {
        deleteLastRecordsJob();

        if (this.findAll().stream().anyMatch(c -> cart.getId().equals(c.getId()))) {
            return false;
        }

        cart.setLastUpdate(new Date());

        return EcommerceApplication.dbCarts.add(cart);
    }

    public Optional<Cart> update(Cart cart) {
        deleteLastRecordsJob();

        Optional<Cart> cartToUpdate = this.findById(cart.getId());
        cart.setLastUpdate(new Date());
        cartToUpdate.ifPresent(c -> EcommerceApplication.dbCarts.set(
                EcommerceApplication.dbCarts.indexOf(c), cart));

        return cartToUpdate.isPresent() ? Optional.of(cart) : Optional.empty();
    }

    public Optional<Cart> partialUpdate(Cart cart) {
        deleteLastRecordsJob();

        Optional<Cart> finalCart = Optional.empty();
        Optional<Cart> cartToUpdate = this.findById(cart.getId());

        if (cartToUpdate.isPresent()) {
            Cart c = cartToUpdate.get();

            if (cart.getProducts() != null) {
                c.setProducts(cart.getProducts());
                c.setLastUpdate(new Date());
            }

            finalCart = Optional.of(c);
        }

        return finalCart;
    }

    public boolean delete(Integer id) {
        deleteLastRecordsJob();

        Optional<Cart> cartToDelete = this.findById(id);
        cartToDelete.ifPresent(c -> EcommerceApplication.dbCarts.remove(c));

        return cartToDelete.isPresent();
    }

    private void deleteLastRecordsJob() {
        List<Cart> elementsToDelete = EcommerceApplication.dbCarts.stream().filter(c -> new Date().getTime() - c.getLastUpdate().getTime() > EcommerceApplication.PROPERTY_TTL).toList();

        EcommerceApplication.dbCarts.removeAll(elementsToDelete);
    }
}
