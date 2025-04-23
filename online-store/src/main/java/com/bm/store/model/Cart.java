package com.bm.store.model;

import com.bm.store.dto.representation.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@Schema(description = "All details about the cart.")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public final class Cart implements StoreUnit {

	/**
	 * selected products in the cart
	 */
	@Schema(description = "Products added to the cart with their quantities")
	private Map<Product, Long> selectedProducts;

	@Schema(description = "Total taxes of the cart")
	private BigDecimal totalTaxes;

	@Schema(description = "Total price of the cart with taxes included")
	private BigDecimal totalPrice;

	@Schema(description = "Cart items that links products and quantities for JSON representation")
	private List<CartItem> cartItems;

}
