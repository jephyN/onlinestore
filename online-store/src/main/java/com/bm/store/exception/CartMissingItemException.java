package com.bm.store.exception;

public class CartMissingItemException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CartMissingItemException(int id) {
		super("Could not find the product " + id + " in the cart.");
	}
}
