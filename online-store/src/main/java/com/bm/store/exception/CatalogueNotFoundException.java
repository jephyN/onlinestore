package com.bm.store.exception;

import java.io.Serial;

public class CatalogueNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public CatalogueNotFoundException(int id) {
		super("Could not find the catalogue " + id);
	}
}
