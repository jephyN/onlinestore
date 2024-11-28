package com.bm.store.exception;

import java.io.Serial;

public class ProductNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(long id) {
		super("Could not find the product " + id);
	}
}
