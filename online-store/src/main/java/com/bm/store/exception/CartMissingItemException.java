package com.bm.store.exception;

import java.io.Serial;

/**
 * Exception levée lorsque l'article du panier est manquant.
 */
public class CartMissingItemException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Construire une nouvelle exception pour un article manquant dans le panier.
	 *
	 * @param id L'identifiant de l'article.
	 */
	public CartMissingItemException(long id) {
		super("Could not find the product " + id + " in the cart.");
	}
}
