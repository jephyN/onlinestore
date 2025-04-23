package com.bm.store.dto.representation.model;

import com.bm.store.dto.representation.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@Schema(description = "All details about the cart.")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CartModel extends RepresentationModel<CartModel> {

	@Schema(description = "Total taxes of the cart")
	private BigDecimal totalTaxes;

	@Schema(description = "Total price of the cart with taxes included")
	private BigDecimal totalPrice;

	@Schema(description = "Cart items that links products and quantities for JSON representation")
	private List<CartItem> cartItems;

}
