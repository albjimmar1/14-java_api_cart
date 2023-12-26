package com.example.ecommerce;

import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.Product;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="E-commerce API", version = "1.0", description = "E-commerce microservice"))
public class EcommerceApplication {

	public static List<Cart> dbCarts;
	public static List<Product> dbProducts;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);

		EcommerceApplication.dbProducts = EcommerceApplication.getProducts();
		EcommerceApplication.dbCarts = EcommerceApplication.getCarts();
	}

	private static ArrayList<Product> getProducts() {
		ArrayList<Product> products = new ArrayList<>();

		products.add(new Product(1, "Play 5", 1));
		products.add(new Product(2, "Xbox Series X", 2));
		products.add(new Product(3, "Xbox Series S", 3));
		products.add(new Product(4, "Nintendo Switch", 4));

		return products;
	}

	private static ArrayList<Cart> getCarts() {
		ArrayList<Cart> carts = new ArrayList<>();

		carts.add(new Cart(1, getProducts()));

		return carts;
	}

}
