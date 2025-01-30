package com.bm.store.dto;

import com.bm.store.model.Product;
import lombok.Builder;

import java.util.Objects;

public record CartItem(Product product, long quantity) {
	@Builder
	public CartItem {
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		CartItem cartItem = (CartItem) o;
		return Objects.equals(product, cartItem.product);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(product);
	}
}
