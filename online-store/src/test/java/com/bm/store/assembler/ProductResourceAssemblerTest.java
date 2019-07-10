package com.bm.store.assembler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Resource;

import com.bm.store.model.Product;

public class ProductResourceAssemblerTest {

	ProductResourceAssembler assembler;

	@Before
	public void setUp() throws Exception {
		assembler = new ProductResourceAssembler();
	}

	@Test
	public void buildProductResource() {
		/* Given */
		Product product = new Product();
		product.setId(1);
		product.setName("test");

		/* When */
		Resource<Product> resource = assembler.toResource(product);

		/* Then */
		assertNotNull(resource);
		assertEquals(product, resource.getContent());
		assertNotNull(resource.getId());
	}

}
