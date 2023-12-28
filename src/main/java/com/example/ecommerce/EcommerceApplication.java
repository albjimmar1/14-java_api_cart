package com.example.ecommerce;

import com.example.ecommerce.models.Cart;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="E-commerce API", version = "1.0", description = "E-commerce microservice"))
public class EcommerceApplication {

	public static final long PROPERTY_TTL = 600000;
	public static List<Cart> dbCarts;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);

		EcommerceApplication.dbCarts = new ArrayList<>();
	}

}
