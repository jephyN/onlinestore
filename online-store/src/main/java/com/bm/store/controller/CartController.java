package com.bm.store.controller;

import com.bm.store.assembler.CartResourceAssembler;
import com.bm.store.dao.ProductRepository;
import com.bm.store.exception.CartMissingItemException;
import com.bm.store.exception.ProductNotFoundException;
import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import com.bm.store.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@Api(value = "Cart Management")
@Slf4j
public class CartController {

    @javax.annotation.Resource(name = "customerCart")
    private Cart cart;
    private final CartResourceAssembler cartResourceAssembler;
    private final CartService cartService;
    private final ProductRepository productRepository;

    public CartController(CartResourceAssembler cartResourceAssembler,
                          CartService cartService, ProductRepository productRepository) {
        this.cartResourceAssembler = cartResourceAssembler;
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    @ApiOperation(value = "Read cart's information")
    @GetMapping
    public EntityModel<Cart> readCart() {
        log.info("Reading a cart ...");
        return cartResourceAssembler.toModel(cart);
    }

    @ApiOperation(value = "Add a product in the cart")
    @PatchMapping("/product/{id}")
    public EntityModel<Cart> addProductToCart(
            @ApiParam(value = "Product id to add", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "quantity to add", required = true) @RequestParam("qt") long qt) {
        log.info("adding a product to cart ...");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        cartService.addCartItems(cart, product, qt);
        return cartResourceAssembler.toModel(cart);
    }

    @ApiOperation(value = "Remove a product from cart")
    @DeleteMapping("/product/{id}")
    public EntityModel<Cart> removeProductFromCart(
            @ApiParam(value = "Product id to remove", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "quantity to remove", required = true) @RequestParam("qt") long qt) {
        log.info("removing a product to cart ...");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        validate(id, product);
        cartService.removeCartItems(cart, product, qt);
        return cartResourceAssembler.toModel(cart);
    }

    private void validate(long id, Product product) {
        Optional.of(cart.getSelectedProducts())
                .filter(productMap -> productMap.containsKey(product))
                .orElseThrow(() -> new CartMissingItemException(id));
    }
}
