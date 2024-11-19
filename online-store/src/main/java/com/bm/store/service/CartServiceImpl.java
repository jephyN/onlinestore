package com.bm.store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bm.store.model.Cart;
import com.bm.store.model.CartItem;
import com.bm.store.model.Product;

@Service
public class CartServiceImpl implements CartService {

	private static final double TAX = 0.13;

	@Override
	public void addCartItems(Cart cart, Product product, long quantity) {
		cart.getSelectedProducts().merge(product, quantity, Long::sum);
		generatesPricing(cart);
	}

	@Override
	public void removeCartItems(Cart cart, Product product, long quantity) {
		cart.getSelectedProducts().merge(product, quantity, (q1, q2) -> q1 - q2);
		if (cart.getSelectedProducts().get(product) <= 0L) {
			cart.getSelectedProducts().remove(product);
		}
		generatesPricing(cart);
	}

	private void generatesPricing(Cart cart) {
		manageCartItems(cart);
		calculateTaxes(cart);
		calculateTotalPrice(cart);
	}

	private void manageCartItems(Cart cart) {
		List<CartItem> cartItems = new ArrayList<>();
		cart.getSelectedProducts().forEach((k, v) -> {
			CartItem cartItem = CartItem.builder().id(k.getId()).productCode(k.getProductCode()).name(k.getName())
					.productType(k.getProductType()).description(k.getDescription()).imageUrl(k.getImageUrl())
					.isTaxable(k.isTaxable()).price(k.getPrice()).quantity(v).build();
			cartItems.add(cartItem);
		});
		cart.setCartItems(cartItems);
	}

	private void calculateTaxes(Cart cart) {
		Double totalTaxes = cart.getCartItems().stream()
				.map(item -> item.getPrice().multiply(BigDecimal.valueOf(TAX))
						.multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		cart.setTotalTaxes(totalTaxes);
	}

	private void calculateTotalPrice(Cart cart) {
		Double total = cart.getCartItems().stream()
				.map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_EVEN)
				.add(BigDecimal.valueOf(cart.getTotalTaxes()))
				.doubleValue();
		cart.setTotalPrice(total);
	}

}
