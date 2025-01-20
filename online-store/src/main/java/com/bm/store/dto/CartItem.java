package com.bm.store.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false, exclude = {"quantity"})
public class CartItem {
	private final Product product;
	private final long quantity;

	@Builder
	public CartItem(Product product, long quantity) {
		this.product = product;
		this.quantity = quantity;
	}

}
