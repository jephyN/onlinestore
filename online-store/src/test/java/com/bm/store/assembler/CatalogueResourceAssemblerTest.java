package com.bm.store.assembler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Resource;

import com.bm.store.model.Catalogue;
import com.bm.store.model.Product;

public class CatalogueResourceAssemblerTest {

	CatalogueResourceAssembler assembler;

	@Before
	public void setUp() throws Exception {
		assembler = new CatalogueResourceAssembler();
	}

	@Test
	public void buildCatalogResource() {
		/* Given */
		Product product = new Product();
		Set<Product> products = new HashSet<>();
		product.setId(1);
		products.add(product);

		Catalogue catalogue = Catalogue.builder().id(1).startDate(LocalDate.now())
				.endDate(LocalDate.now().plusMonths(3)).catalogProducts(products).build();

		/* When */
		Resource<Catalogue> resource = assembler.toResource(catalogue);

		/* Then */
		assertNotNull(resource);
		assertEquals(catalogue, resource.getContent());
		assertNotNull(resource.getId());
	}

}
