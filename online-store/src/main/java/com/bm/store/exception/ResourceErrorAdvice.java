package com.bm.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ResourceErrorAdvice {
	
	@ResponseBody
	@ExceptionHandler(CatalogueNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String catalogueHandler(CatalogueNotFoundException exception) {
		log.error("Catalogue not found!");
		return exception.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String productHandler(ProductNotFoundException exception) {
		log.error("Product not found!");
		return exception.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(CartMissingItemException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String cartHandler(CartMissingItemException exception) {
		log.error("Product not found in the cart!");
		return exception.getMessage();
	}

}
