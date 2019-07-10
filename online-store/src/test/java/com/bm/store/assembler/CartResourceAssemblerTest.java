package com.bm.store.assembler;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Resource;

import com.bm.store.model.Cart;
import com.bm.store.model.Product;

public class CartResourceAssemblerTest {

	CartResourceAssembler assembler;

	@Before
	public void setUp() throws Exception {
		assembler = new CartResourceAssembler();
	}

	@Test
	public void buildCartResourceNoProduct() {
		/* Given */
		Product product = new Product();
		product.setId(1);

		Cart cart = Cart.builder().selectedProducts(new LinkedHashMap<>()).build();

		/* When */
		Resource<Cart> resource = assembler.toResource(cart);

		/* Then */
		assertNotNull(resource);
		assertEquals(cart, resource.getContent());
		assertNotNull(resource.getId());
		assertNotNull(resource.getLinks());
		assertThat(resource.getLinks(), hasSize(1));
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
		Resource<Cart> resource = assembler.toResource(cart);

		/* Then */
		assertNotNull(resource);
		assertEquals(cart, resource.getContent());
		assertNotNull(resource.getId());
		assertNotNull(resource.getLinks());
		assertThat(resource.getLinks(), Matchers.hasSize(2));
		assertTrue(resource.hasLink("products"));
	}

}
