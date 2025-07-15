package com.bm.store.exception;

import java.io.Serial;

/**
 * Exception raised when the product is not found.
 */
public class ProductNotFoundException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Build a new exception for a product not found.
	 *
	 * @param id The product id.
	 */
	public ProductNotFoundException(long id) {
		super("Could not find the product " + id);
	}
}
