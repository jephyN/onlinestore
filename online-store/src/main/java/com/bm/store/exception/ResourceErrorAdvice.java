package com.bm.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * Gestionnaire global des exceptions pour l'application.
 */
@ControllerAdvice
@Slf4j
public class ResourceErrorAdvice {

	private static final String MESSAGE_ERREUR_INTROUVABLE = "Ressource introuvable : ";

	/**
	 * Gère les exceptions personnalisées spécifiques et retourne un message
	 * d'erreur.
	 *
	 * @param exception L'exception levée.
	 * @return Le message d'erreur.
	 */
	@ResponseBody
	@ExceptionHandler({ CatalogueNotFoundException.class, ProductNotFoundException.class, CartMissingItemException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String resourceNotFoundHandler(RuntimeException exception) {
        log.error(MESSAGE_ERREUR_INTROUVABLE + "{}", exception.getMessage());
		return exception.getMessage();
	}
}
