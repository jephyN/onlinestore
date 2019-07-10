package com.bm.store;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bm.store.model.Cart;
import com.bm.store.model.Product;

@SpringBootApplication
public class OnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApplication.class, args);
	}
	
	@Bean(name = "customerCart")
	public Cart customerCart() {
		return Cart.builder()
				.selectedProducts(new LinkedHashMap<Product, Long>())
				.totalPrice(0d)
				.totalTaxes(0d)
				.cartItems(new ArrayList<>())
				.build();
	}

}
