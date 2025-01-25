package com.bm.store.dto;

import com.bm.store.model.Product;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
