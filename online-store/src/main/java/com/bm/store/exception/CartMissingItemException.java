package com.bm.store.exception;

import java.io.Serial;

public class CartMissingItemException extends RuntimeException {
	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public CartMissingItemException(long id) {
		super("Could not find the product " + id + " in the cart.");
	}
}
