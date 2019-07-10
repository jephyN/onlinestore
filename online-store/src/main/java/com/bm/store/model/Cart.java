package com.bm.store.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@ApiModel(description = "All details about the cart. ")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Cart extends StoreUnit {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	/**
	 * selected products in the cart
	 */
	@JsonIgnore
	@ApiModelProperty(notes = "Products added to the cart with their quantities")
	private Map<Product, Long> selectedProducts;
	
	@ApiModelProperty(notes = "Total taxes of the cart")
	private Double totalTaxes;

	@ApiModelProperty(notes = "Total price of the cart with taxes included")
	private Double totalPrice;

	@ApiModelProperty(notes = "Cart items that links products and quantities for JSON representation")
	private List<CartItem> cartItems;

}
