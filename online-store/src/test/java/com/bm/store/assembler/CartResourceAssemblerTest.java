package com.bm.store.assembler;

import com.bm.store.dto.CartItem;
import com.bm.store.dto.CartModel;
import com.bm.store.mapper.CartMapper;
import com.bm.store.mapper.CartMapperImpl;
import com.bm.store.mapper.ProductMapper;
import com.bm.store.mapper.ProductMapperImpl;
import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.IanaLinkRelations;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartResourceAssemblerTest {

    CartResourceAssembler assembler;

    @BeforeEach
    void setUp() {
        CartMapper cartMapper = new CartMapperImpl();
        ProductMapper productMapper = new ProductMapperImpl();
        ProductResourceAssembler productResourceAssembler = new ProductResourceAssembler(productMapper);
        assembler = new CartResourceAssembler(cartMapper, productResourceAssembler);
    }

    @Test
    void buildCartResourceNoProduct() {
        /* Given */
        Cart cart = Cart.builder().selectedProducts(new LinkedHashMap<>()).build();

        /* When */
        CartModel resource = assembler.toModel(cart);

        /* Then */
        assertThat(resource).isNotNull();
        assertThat(resource).hasAllNullFieldsOrPropertiesExcept("cartItems", "links");
        assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
        assertThat(resource.getLinks()).isNotNull().hasSize(1);
    }

    @Test
    void buildCartResourceWithProducts() {
        /* Given */
        Product product = new Product();
        product.setPrice(BigDecimal.TEN);
        product.setTaxable(Boolean.TRUE);
        product.setId(1);

        CartItem cartItem = CartItem.builder()
                .product(product)
                .quantity(2L)
                .build();

        List<CartItem> cartItems = List.of(cartItem);
        Cart cart = Cart.builder()
                .cartItems(cartItems)
                .totalTaxes(1.3D)
                .totalPrice(11.3D)
                .build();

        /* When */
        CartModel resource = assembler.toModel(cart);

        /* Then */
        assertThat(resource).isNotNull();
        assertThat(resource).hasNoNullFieldsOrProperties();
        assertThat(resource).extracting("cartItems", "totalTaxes", "totalPrice").contains(cartItems,1.3D, 11.3D);
        assertThat(resource.getRequiredLink(IanaLinkRelations.SELF)).isNotNull();
        assertThat(resource.getLinks()).isNotNull().hasSize(2);
        assertThat(resource.hasLink("products")).isTrue();
    }

}
