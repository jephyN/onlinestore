package com.bm.store.exception;

import java.io.Serial;

/**
 * Exception raised when the catalog is not found.
 */
public class CatalogueNotFoundException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Build a new exception for a catalog not found.
	 *
	 * @param id The identifier of the catalog.
	 */
	public CatalogueNotFoundException(int id) {
		super("Could not find the catalogue " + id);
	}
}
