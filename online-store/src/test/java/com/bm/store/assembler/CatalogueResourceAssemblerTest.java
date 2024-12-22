package com.bm.store.assembler;

import com.bm.store.dto.CatalogueModel;
import com.bm.store.dto.ProductModel;
import com.bm.store.mapper.ProductMapper;
import com.bm.store.mapper.ProductMapperImpl;
import com.bm.store.model.Catalogue;
import com.bm.store.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.hateoas.IanaLinkRelations;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogueResourceAssemblerTest {

    CatalogueResourceAssembler assembler;
    ProductResourceAssembler productResourceAssembler;
    ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapperImpl();
        productResourceAssembler = new ProductResourceAssembler(productMapper);
        assembler = new CatalogueResourceAssembler(productResourceAssembler);
    }

    @Test
    void toModel_whenCatalogueNotEmpty_shouldReturnNonEmptyCatalogueModel() {
        /* Given */
        Product product = new Product();
        Set<Product> products = new HashSet<>();
        product.setId(1);
        product.setName("myProduct");
        products.add(product);

        Catalogue catalogue = Catalogue.builder()
                .id(1)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .catalogProducts(products)
                .build();

        /* When */
        CatalogueModel resource = assembler.toModel(catalogue);

        /* Then */
        assertThat(resource).isNotNull();
        assertThat(resource).extracting("id", "startDate", "endDate", "catalogProducts")
                .contains(1, LocalDate.now(), LocalDate.now().plusMonths(3),
                        Set.of(ProductModel.builder().id(1).name("myProduct").build()));
        assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void toModel_whenCatalogueHasNoProducts_shouldReturnCatalogueModelWithoutProducts(Set<Product> products) {
        /* Given */
        Catalogue catalogue = Catalogue.builder()
                .id(1)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .catalogProducts(products)
                .build();

        /* When */
        CatalogueModel resource = assembler.toModel(catalogue);

        /* Then */
        assertThat(resource).isNotNull();
        assertThat(resource).extracting("id", "startDate", "endDate", "catalogProducts")
                .contains(1, LocalDate.now(), LocalDate.now().plusMonths(3), Set.of());
        assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
    }

}
