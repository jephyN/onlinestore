package com.bm.store.assembler;

import com.bm.store.dto.ProductModel;
import com.bm.store.mapper.ProductMapper;
import com.bm.store.mapper.ProductMapperImpl;
import com.bm.store.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.IanaLinkRelations;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResourceAssemblerTest {

    ProductResourceAssembler assembler;

    @BeforeEach
    void setUp() {
        ProductMapper productMapper = new ProductMapperImpl();
        assembler = new ProductResourceAssembler(productMapper);
    }

    @Test
    void toModel_whenProductExists_shouldReturnProduct() {
        /* Given */
        Product product = new Product();
        product.setId(1);
        product.setName("test");

        /* When */
        ProductModel resource = assembler.toModel(product);

        /* Then */
        assertThat(resource).isNotNull();
        assertThat(resource).extracting("id", "name")
                .contains(1L, "test");
        assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
    }

}
