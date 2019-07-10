package com.bm.store.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "All details about the cart item. ")
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"quantity"})
public class CartItem extends Product {

	@ApiModelProperty(notes = "The quantity of the cart item")
	private long quantity;

	@Builder 
	public CartItem(long id, String productCode, String name, String productType, String description, String imageUrl,
			BigDecimal price,boolean isTaxable, long quantity) {
		super(id, productCode, name, productType, description, imageUrl, price, isTaxable);
		this.quantity = quantity;
	}

}
