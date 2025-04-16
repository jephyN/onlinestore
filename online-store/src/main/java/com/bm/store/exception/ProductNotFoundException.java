package com.bm.store.exception;

import java.io.Serial;

/**
 * Exception levée lorsque le produit est introuvable.
 */
public class ProductNotFoundException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Construire une nouvelle exception pour un produit introuvable.
	 *
	 * @param id L'identifiant du produit.
	 */
	public ProductNotFoundException(long id) {
		super("Could not find the product " + id);
	}
}
