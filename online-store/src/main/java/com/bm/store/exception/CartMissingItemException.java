package com.bm.store.exception;

import java.io.Serial;

/**
 * Exception raised when the basket item is missing.
 */
public class CartMissingItemException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Build a new exception for a missing item in the cart.
	 *
	 * @param id The article ID.
	 */
	public CartMissingItemException(long id) {
		super("Could not find the product " + id + " in the cart.");
	}
}
