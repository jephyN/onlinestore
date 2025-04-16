package com.bm.store.exception;

import java.io.Serial;

/**
 * Exception levée lorsque le catalogue est introuvable.
 */
public class CatalogueNotFoundException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Construire une nouvelle exception pour un catalogue introuvable.
	 *
	 * @param id L'identifiant du catalogue.
	 */
	public CatalogueNotFoundException(int id) {
		super("Could not find the catalogue " + id);
	}
}
