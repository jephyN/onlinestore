package com.bm.store.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Products REST API")
						.description("Manage online products.")
						.version("v0.0.1")
						.contact(new Contact().name("Jephy Ntutu-Di-Beti")
								.email("jephy.ntutu@gmail.com")));
	}
}
