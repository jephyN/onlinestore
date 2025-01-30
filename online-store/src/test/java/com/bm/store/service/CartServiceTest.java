package com.bm.store.service;

import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import com.bm.store.utils.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class CartServiceTest {

    CartService cartService;

    @BeforeEach
    void setUp() {
        Calculator calculator = new Calculator(0.13);
        cartService = new CartServiceImpl(calculator);
    }

    @Test
    void addFirstCartItems_whenCartIsEmpty_shouldReturnProducts() {
        /* Given */
        Cart cart = Cart.builder().selectedProducts(new HashMap<>()).build();
        Product product = new Product();
        product.setId(1);
        product.setPrice(BigDecimal.valueOf(9.99));
        product.setTaxable(true);
        long quantity = 1L;

        /* When */
        cartService.addCartItems(cart, product, quantity);

        /* Then */
        assertThat(cart.getSelectedProducts())
                .hasSize(1)
                .containsKey(product)
                .containsValue(quantity);
        assertThat(cart.getTotalTaxes()).isEqualTo(BigDecimal.valueOf(1.30D).setScale(2));
        assertThat(cart.getTotalPrice()).isEqualTo(BigDecimal.valueOf(11.29D));
        assertThat(cart.getCartItems()).hasSize(1);
    }

    @Test
    void addCartSameProductItems_withSameProducts_shouldReturnSameProducts() {
        /* Given */
        Cart cart = Cart.builder().selectedProducts(new HashMap<>()).build();
        Product product = new Product();
        product.setId(1);
        product.setPrice(BigDecimal.valueOf(9.99));
        product.setTaxable(true);
        long quantity = 1L;

        /* When */
        cartService.addCartItems(cart, product, quantity);
        cartService.addCartItems(cart, product, quantity);

        /* Then */
        assertThat(cart.getSelectedProducts())
                .hasSize(1)
                .containsKey(product)
                .containsValue(quantity * 2);
        assertThat(cart.getTotalTaxes()).isEqualTo(BigDecimal.valueOf(2.60D).setScale(2));
        assertThat(cart.getTotalPrice()).isEqualTo(BigDecimal.valueOf(22.58D));
        assertThat(cart.getCartItems()).hasSize(1);
    }

    @Test
    void removeCartProductItems_whenCartHasOneProduct_shouldRemoveProduct() {
        /* Given */
        Cart cart = Cart.builder().selectedProducts(new HashMap<>()).build();
        Product product = new Product();
        product.setId(1);
        product.setPrice(BigDecimal.valueOf(9.99));
        product.setTaxable(true);
        long quantity = 1L;

        /* When */
        cartService.addCartItems(cart, product, quantity);
        cartService.removeCartItems(cart, product, quantity);

        /* Then */
        assertThat(cart.getSelectedProducts()).isEmpty();
        assertThat(cart.getTotalTaxes()).isZero();
        assertThat(cart.getTotalPrice()).isZero();
        assertThat(cart.getCartItems()).isEmpty();
    }

    @Test
    void removeCartProductItems_whenQuantityToRemoveExceeds_shouldRemoveProduct() {
        /* Given */
        Cart cart = Cart.builder().selectedProducts(new HashMap<>()).build();
        Product product = new Product();
        product.setId(1);
        product.setPrice(BigDecimal.valueOf(9.99));
        product.setTaxable(true);
        long quantity = 1L;

        /* When */
        cartService.addCartItems(cart, product, quantity);
        cartService.removeCartItems(cart, product, quantity + 1);

        /* Then */
        assertThat(cart.getSelectedProducts()).isEmpty();
        assertThat(cart.getTotalTaxes()).isZero();
        assertThat(cart.getTotalPrice()).isZero();
        assertThat(cart.getCartItems()).isEmpty();
    }
}
