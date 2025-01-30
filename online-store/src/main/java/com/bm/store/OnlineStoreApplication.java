package com.bm.store;

import com.bm.store.model.Cart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@SpringBootApplication
public class OnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApplication.class, args);
	}
	
	@Bean(name = "customerCart")
	public Cart customerCart() {
		return Cart.builder()
				.selectedProducts(new LinkedHashMap<>())
				.totalPrice(BigDecimal.ZERO)
				.totalTaxes(BigDecimal.ZERO)
				.cartItems(new ArrayList<>())
				.build();
	}

}
