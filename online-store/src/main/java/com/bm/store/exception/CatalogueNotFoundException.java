package com.bm.store.exception;

public class CatalogueNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CatalogueNotFoundException(int id) {
		super("Could not find the catalogue " + id);
	}

	
}
