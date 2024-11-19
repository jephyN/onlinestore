package com.bm.store.assembler;

import com.bm.store.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResourceAssemblerTest {

	ProductResourceAssembler assembler;

	@BeforeEach
	void setUp() {
		assembler = new ProductResourceAssembler();
	}

	@Test
	void buildProductResource() {
		/* Given */
		Product product = new Product();
		product.setId(1);
		product.setName("test");

		/* When */
		EntityModel<Product> resource = assembler.toModel(product);

		/* Then */
		assertThat(resource).isNotNull();
		assertThat(product).isEqualTo(resource.getContent());
		assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
	}

}
