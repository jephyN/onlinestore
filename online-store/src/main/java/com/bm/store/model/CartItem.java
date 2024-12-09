package com.bm.store.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false, exclude = {"quantity"})
public final class CartItem extends Product {

	private final long quantity;

	@Builder 
	public CartItem(long id, String productCode, String name, String productType, String description, String imageUrl,
			BigDecimal price,boolean isTaxable, long quantity) {
		super(id, productCode, name, productType, description, imageUrl, price, isTaxable);
		this.quantity = quantity;
	}

}
