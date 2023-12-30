package com.example.ecommerce.controllers;

import com.example.ecommerce.builders.ResponseBuilder;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/v1/")
public class CartController {

    @Autowired
    CartService cartService;


    @GetMapping("/carts")
    @Tag(name = "#Get all carts")
    @Operation(
            description = "Get all carts.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }
                    ),
                    @ApiResponse(
                            description = "No content.",
                            responseCode = "204",
                            content = { @Content(mediaType = "application/json") }
                    )
            }
    )
    public ResponseEntity<Object> getCarts() {
        ResponseBuilder response = this.cartService.findAll();

        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/carts/{idCart}")
    @Tag(name = "#Get cart by id")
    @Operation(
            description = "Get cart by id.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Not found.",
                            responseCode = "404",
                            content = { @Content(mediaType = "application/json") }
                    )
            }
    )
    public ResponseEntity<Object> getCartById(@PathVariable Integer idCart) {
        ResponseBuilder response = this.cartService.findById(idCart);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/carts")
    @Tag(name = "#Create a card")
    @Operation(
            description = "Create a card.",
            responses = {
                    @ApiResponse(
                            description = "Created.",
                            responseCode = "201",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Conflict.",
                            responseCode = "409",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Bad request.",
                            responseCode = "400",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    )
            }
    )
    public ResponseEntity<Object> addCart(@RequestBody Cart cart) {
        ResponseBuilder response = this.cartService.add(cart);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/carts/{idCart}")
    @Tag(name = "#Completely update a cart")
    @Operation(
            description = "Completely update a cart.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Not found.",
                            responseCode = "404",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Bad request.",
                            responseCode = "400",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    )
            }
    )
    public ResponseEntity<Object> updateCart(@RequestBody Cart cart, @PathVariable Integer idCart) {
        ResponseBuilder response = this.cartService.update(cart, idCart);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/carts/{idCart}")
    @Tag(name = "#Partial update a cart")
    @Operation(
            description = "Partial update a cart.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Not found.",
                            responseCode = "404",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Bad request.",
                            responseCode = "400",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    )
            }
    )
    public ResponseEntity<Object> partialUpdateCart(@RequestBody Cart cart, @PathVariable Integer idCart) {
        ResponseBuilder response = this.cartService.partialUpdate(cart, idCart);

        return new ResponseEntity<>(response, response.getStatus());
    }


    @DeleteMapping("/carts/{idCart}")
    @Tag(name = "#Delete a cart")
    @Operation(
            description = "Delete a cart.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json") }
                    ),
                    @ApiResponse(
                            description = "Not found.",
                            responseCode = "404",
                            content = { @Content(mediaType = "application/json") }
                    )
            }
    )
    public ResponseEntity<Object> deleteCart(@PathVariable Integer idCart) {
        ResponseBuilder response = this.cartService.delete(idCart);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/carts/{idCart}/products")
    @Tag(name = "#Get all products from a cart")
    @Operation(
            description = "Get all products from a cart.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }
                    ),
                    @ApiResponse(
                            description = "Not found.",
                            responseCode = "404",
                            content = { @Content(mediaType = "application/json") }
                    )
            }
    )
    public ResponseEntity<Object> getAllProductsFromCart(@PathVariable Integer idCart) {
        ResponseBuilder response = this.cartService.findAllProducts(idCart);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/carts/{idCart}/products/{idProduct}")
    @Tag(name = "#Get a product by id from a cart")
    @Operation(
            description = "Get a product by id from a cart.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }
                    ),
                    @ApiResponse(
                            description = "Not found.",
                            responseCode = "404",
                            content = { @Content(mediaType = "application/json") }
                    )
            }
    )
    public ResponseEntity<Object> getProductByIdFromCart(@PathVariable Integer idCart, @PathVariable Integer idProduct) {
        ResponseBuilder response = this.cartService.findProductById(idCart, idProduct);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/carts/{idCart}/products")
    @Tag(name = "#Add products to a cart")
    @Operation(
            description = "Add products to a cart.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Not found.",
                            responseCode = "404",
                            content = { @Content(mediaType = "application/json") }
                    ),
                    @ApiResponse(
                            description = "Bad request.",
                            responseCode = "400",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    )
            }
    )
    public ResponseEntity<Object> addProductsToCart(@PathVariable Integer idCart, @RequestBody List<Product> products) {
        ResponseBuilder response = this.cartService.addProduct(idCart, products);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PatchMapping("/carts/{idCart}/products/{idProduct}")
    @Tag(name = "#Remove a product from a cart")
    @Operation(
            description = "Remove a product from a cart.",
            responses = {
                    @ApiResponse(
                            description = "Ok.",
                            responseCode = "200",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }
                    ),
                    @ApiResponse(
                            description = "Not found.",
                            responseCode = "404",
                            content = { @Content(mediaType = "application/json") }
                    ),
                    @ApiResponse(
                            description = "Bad request.",
                            responseCode = "400",
                            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }
                    )
            }
    )
    public ResponseEntity<Object> removeProductToCart(@PathVariable Integer idCart, @PathVariable Integer idProduct, @RequestBody Product product) {
        ResponseBuilder response = this.cartService.removeProduct(idCart, idProduct, product);

        return new ResponseEntity<>(response, response.getStatus());
    }
}
