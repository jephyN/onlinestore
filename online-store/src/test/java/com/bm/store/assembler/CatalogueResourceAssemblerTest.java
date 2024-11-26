package com.bm.store.assembler;

import com.bm.store.model.Catalogue;
import com.bm.store.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogueResourceAssemblerTest {

	CatalogueResourceAssembler assembler;

	@BeforeEach
	void setUp() {
		assembler = new CatalogueResourceAssembler();
	}

	@Test
	void buildCatalogResource() {
		/* Given */
		Product product = new Product();
		Set<Product> products = new HashSet<>();
		product.setId(1);
		products.add(product);

		Catalogue catalogue = Catalogue.builder()
				.id(1)
				.startDate(LocalDate.now())
				.endDate(LocalDate.now().plusMonths(3))
				.catalogProducts(products)
				.build();

		/* When */
		EntityModel<Catalogue> resource = assembler.toModel(catalogue);

		/* Then */
		assertThat(resource).isNotNull();
		assertThat(catalogue).isEqualTo(resource.getContent());
		assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
	}

}
