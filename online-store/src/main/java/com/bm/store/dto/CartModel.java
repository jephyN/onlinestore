package com.bm.store.dto;

import com.bm.store.model.CartItem;
import com.bm.store.model.Product;
import com.bm.store.model.StoreUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

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
public final class CartModel extends RepresentationModel<CartModel> {

	@Schema(description = "Total taxes of the cart")
	private Double totalTaxes;

	@Schema(description = "Total price of the cart with taxes included")
	private Double totalPrice;

	@Schema(description = "Cart items that links products and quantities for JSON representation")
	private List<CartItem> cartItems;

}
