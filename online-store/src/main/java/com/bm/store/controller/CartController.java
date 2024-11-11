package com.bm.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bm.store.assembler.CartResourceAssembler;
import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import com.bm.store.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cart")
@Api(value = "Cart Management")
@Slf4j
public class CartController {

	@javax.annotation.Resource(name = "customerCart")
	private Cart cart;

	@Autowired
	private CartResourceAssembler cartResourceAssembler;

	@Autowired
	private ProductController productController;
	
	@Autowired
	private CartService cartService;

	@ApiOperation(value = "Read cart's information")
	@GetMapping
	public EntityModel<Cart> readCart() {
		log.info("Reading a cart ...");
		return cartResourceAssembler.toModel(cart);
	}

	@ApiOperation(value = "Add a product in the cart")
	@PostMapping("/product/{id}")
	public EntityModel<Cart> addProductToCart(
			@ApiParam(value = "Catalog id to read mission object", required = true) @PathVariable(value = "id") int id,
			@RequestParam("qt") long qt) {
		log.info("adding a product to cart ...");

		EntityModel<Product> productResource = productController.readProduct(id);
		
		cartService.addCartItems(cart, productResource.getContent(), qt);

		return cartResourceAssembler.toModel(cart);
	}

	@ApiOperation(value = "Remove a product from cart")
	@DeleteMapping("/product/{id}")
	public EntityModel<Cart> removeProductFromCart(
			@ApiParam(value = "Catalog id to read mission object", required = true) @PathVariable(value = "id") int id,
			@RequestParam("qt") long qt) {
		log.info("adding a product to cart ...");

		EntityModel<Product> productResource = productController.readProduct(id);
		
		cartService.removeCartItems(cart, productResource.getContent(), qt);
		
		return cartResourceAssembler.toModel(cart);
	}
}
