package com.bm.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception manager for the application.
 */
@ControllerAdvice
@Slf4j
public class ResourceErrorAdvice {

	private static final String MESSAGE_ERREUR_INTROUVABLE = "Resource not found : ";
	public static final String VALEUR_INVALIDE = "Invalid value for the field :";

	/**
	 * Handles specific custom exceptions and returns an error
	 * message.
	 *
	 * @param exception The exception raised.
	 * @return The error message.
	 */
	@ResponseBody
	@ExceptionHandler({ CatalogueNotFoundException.class, ProductNotFoundException.class, CartMissingItemException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ErrorMessage resourceNotFoundHandler(RuntimeException exception) {
        log.error(MESSAGE_ERREUR_INTROUVABLE + "{}", exception.getMessage());
		return ErrorMessage.builder().message(exception.getMessage()).build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorMessage resourceValidationErrorHandler(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
		StringBuilder errorMessage = new StringBuilder();

		if (exception.hasGlobalErrors())
			errorMessage.append(exception.getGlobalError().getDefaultMessage());

		if (exception.hasFieldErrors())
			exception.getFieldErrors()
					.forEach(fieldError ->
							errorMessage.append(VALEUR_INVALIDE)
									.append(fieldError.getField())
									.append(" ")
									.append(fieldError.getDefaultMessage())
									.append(". "));

		return ErrorMessage.builder().message(errorMessage.toString()).build();
	}
}
