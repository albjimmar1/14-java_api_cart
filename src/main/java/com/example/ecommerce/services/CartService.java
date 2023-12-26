package com.example.ecommerce.services;

import com.example.ecommerce.builders.ResponseBuilder;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public ResponseBuilder findAll() {
        List<Cart> results = this.cartRepository.findAll();

        return new ResponseBuilder(
                results,
                results.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK
        );
    }

    public ResponseBuilder findById(Integer id) {
        Optional<Cart> result = this.cartRepository.findById(id);

        return new ResponseBuilder(
                result.orElse(null),
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder add(Cart cart) {
        List<String> errors = new ArrayList<>();

        if (cart.getId() == null)
            errors.add("Cart id is required");
        if (cart.getProducts() == null)
            errors.add("Cart products are required");

        if (!errors.isEmpty()) {
            return new ResponseBuilder(
                    cart,
                    HttpStatus.BAD_REQUEST
            );
        }

        if (this.cartRepository.add(cart)) {
            return new ResponseBuilder(cart, HttpStatus.CREATED);
        } else {
            return new ResponseBuilder(cart, HttpStatus.CONFLICT);
        }
    }

    public ResponseBuilder update(Cart cart, Integer urlId) {
        List<String> errors = new ArrayList<>();

        if (cart.getId() == null)
            errors.add("Cart id is required");
        if (!Objects.equals(cart.getId(), urlId))
            errors.add("Cart id and url id must be the same");

        if (!errors.isEmpty()) {
            return new ResponseBuilder(cart, HttpStatus.BAD_REQUEST);
        }

        Optional<Cart> result = this.cartRepository.update(cart);

        return new ResponseBuilder(
                result.orElse(cart),
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder partialUpdate(Cart cart, Integer urlId) {

        List<String> errors = new ArrayList<>();

        if (cart.getId() == null)
            errors.add("Cart id is required");
        if (!Objects.equals(cart.getId(), urlId))
            errors.add("Cart id and url id must be the same");

        if (!errors.isEmpty()) {
            return new ResponseBuilder(cart, HttpStatus.BAD_REQUEST);
        }

        Optional<Cart> result = this.cartRepository.partialUpdate(cart);

        return new ResponseBuilder(
                result.orElse(cart),
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder delete(Integer id) {
        boolean result = this.cartRepository.delete(id);

        return new ResponseBuilder(
                result ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder findAllProducts(Integer id) {
        Optional<Cart> result = this.cartRepository.findById(id);

        return new ResponseBuilder(
                result.<Object>map(Cart::getProducts).orElse(null),
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder findProductById(Integer idCart, Integer idProduct) {
        Optional<Cart> result = this.cartRepository.findById(idCart);
        Optional<Product> product = Optional.empty();
        List<String> errors = new ArrayList<>();

        if (result.isEmpty()) {
            errors.add("Cart not found");
        } else if (result.get().getProducts() == null) {
            errors.add("Cart does not contain products");
        }

        if (errors.isEmpty()) {
            product = result.get().getProducts().stream().filter(p -> Objects.equals(p.getId(), idProduct)).findFirst();
        }

        return new ResponseBuilder(
                product.orElse(null),
                product.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder addProduct(Integer idCart, Product product) {
        Optional<Cart> result = this.cartRepository.findById(idCart);
        Optional<Cart> finalResult = Optional.empty();

        if (product.getId() == null || (product.getAmount() != null && product.getAmount() <= 0))
            return new ResponseBuilder(product, HttpStatus.BAD_REQUEST);
        if (product.getAmount() == null)
            product.setAmount(1);

        if (result.isPresent()) {
             Cart cartToUpdate = new Cart(result.get());
            if (result.get().getProducts() == null) {
                cartToUpdate.setProducts(List.of(product));
            } else {
                boolean newProduct = true;
                for (Product p : cartToUpdate.getProducts()) {
                    if (Objects.equals(p.getId(), product.getId())) {
                        p.setAmount(p.getAmount() + product.getAmount());
                        newProduct = false;
                    }
                }
                if (newProduct)
                    cartToUpdate.getProducts().add(product);
            }
            finalResult = this.cartRepository.partialUpdate(cartToUpdate);
        }

        return new ResponseBuilder(
                finalResult.orElse(null),
                finalResult.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public ResponseBuilder removeProduct(Integer idCart, Integer idProduct, Product product) {
        Optional<Cart> result = this.cartRepository.findById(idCart);
        Optional<Cart> finalResult = Optional.empty();

        if ((product.getId() != null && !Objects.equals(product.getId(), idProduct))
                || (product.getAmount() != null && product.getAmount() <= 0))
            return new ResponseBuilder(product, HttpStatus.BAD_REQUEST);

        if (result.isPresent() && result.get().getProducts() != null) {
            Cart cartToUpdate = new Cart(result.get());
            boolean updated = false;
            if (product.getAmount() == null) {
                List<Product> listToRemove = cartToUpdate.getProducts().stream().filter(p -> Objects.equals(p.getId(), idProduct)).toList();
                if (!listToRemove.isEmpty()) {
                    cartToUpdate.getProducts().removeAll(listToRemove);
                    updated = true;
                }
            } else {
                for (Product p : cartToUpdate.getProducts()) {
                    if (product.getAmount() != null && Objects.equals(p.getId(), product.getId())) {
                        p.setAmount(Math.max(p.getAmount() - product.getAmount(), 0));
                        updated = true;
                    }
                }
            }
            if (updated)
                finalResult = this.cartRepository.partialUpdate(cartToUpdate);
        }

        return new ResponseBuilder(
                finalResult.orElse(null),
                finalResult.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }
}
