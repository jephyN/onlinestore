package com.bm.store.exception;

public class ProductNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(long id) {
		super("Could not find the product " + id);
	}
}
