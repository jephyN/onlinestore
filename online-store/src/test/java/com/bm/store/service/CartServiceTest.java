package com.bm.store.service;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.bm.store.model.Cart;
import com.bm.store.model.Product;

public class CartServiceTest {
	
	CartService cartService;

	@Before
	public void setUp() {
		cartService = new CartServiceImpl();
	}

	@Test
	public void addFirstCartItems() {
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
		assertEquals(1,cart.getSelectedProducts().size());
		assertTrue(cart.getSelectedProducts().containsKey(product));
		assertTrue(cart.getSelectedProducts().containsValue(quantity));
		assertEquals(Double.valueOf(1.3),cart.getTotalTaxes());
		assertEquals(Double.valueOf(11.29),cart.getTotalPrice());
		assertThat(cart.getCartItems(),hasSize(1));
	}

	@Test
	public void addCartSameProductItems() {
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
		assertEquals(1,cart.getSelectedProducts().size());
		assertTrue(cart.getSelectedProducts().containsKey(product));
		assertTrue(cart.getSelectedProducts().containsValue(quantity*2));
		assertEquals(Double.valueOf(2.6),cart.getTotalTaxes());
		assertEquals(Double.valueOf(22.58),cart.getTotalPrice());
		assertThat(cart.getCartItems(),hasSize(1));
	}
	
	@Test
	public void removeCartProductItems() {
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
		assertEquals(0,cart.getSelectedProducts().size());
		assertFalse(cart.getSelectedProducts().containsKey(product));
		assertFalse(cart.getSelectedProducts().containsValue(quantity));
		assertEquals(Double.valueOf(0.0),cart.getTotalTaxes());
		assertEquals(Double.valueOf(0.0),cart.getTotalPrice());
		assertThat(cart.getCartItems(),hasSize(0));
	}
}
