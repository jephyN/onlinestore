package com.bm.store.assembler;

import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CartResourceAssemblerTest {

	CartResourceAssembler assembler;

	@Before
	public void setUp() {
		assembler = new CartResourceAssembler();
	}

	@Test
	public void buildCartResourceNoProduct() {
		/* Given */
		Product product = new Product();
		product.setId(1);

		Cart cart = Cart.builder().selectedProducts(new LinkedHashMap<>()).build();

		/* When */
		EntityModel<Cart> resource = assembler.toModel(cart);

		/* Then */
		assertThat(resource).isNotNull();
		assertThat(cart).isEqualTo(resource.getContent());
		assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
		assertThat(resource.getLinks()).isNotNull().hasSize(1);
	}

	@Test
	public void buildCartResourceWithProducts() {
		/* Given */
		Product product = new Product();
		product.setId(1);
		Map<Product, Long> map = new LinkedHashMap<>();
		map.put(product, 2L);

		Cart cart = Cart.builder().selectedProducts(map).build();

		/* When */
		EntityModel<Cart> resource = assembler.toModel(cart);

		/* Then */
		assertThat(resource).isNotNull();
		assertThat(cart).isEqualTo(resource.getContent());
		assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
		assertThat(resource.getLinks()).isNotNull().hasSize(2);
		assertThat(resource.hasLink("products")).isTrue();
	}

}
