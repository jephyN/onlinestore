package com.bm.store.controller;

import com.bm.store.assembler.CartResourceAssembler;
import com.bm.store.exception.CartMissingItemException;
import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import com.bm.store.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
    private final ProductController productController;
    private final CartService cartService;

    public CartController(CartResourceAssembler cartResourceAssembler, ProductController productController,
                          CartService cartService) {
        this.cartResourceAssembler = cartResourceAssembler;
        this.productController = productController;
        this.cartService = cartService;
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
            @ApiParam(value = "Product id to add", required = true) @PathVariable(value = "id") int id,
            @ApiParam(value = "quantity to add", required = true) @RequestParam("qt") long qt) {
        log.info("adding a product to cart ...");
        EntityModel<Product> productResource = productController.readProduct(id);
        cartService.addCartItems(cart, productResource.getContent(), qt);
        return cartResourceAssembler.toModel(cart);
    }

    @ApiOperation(value = "Remove a product from cart")
    @DeleteMapping("/product/{id}")
    public EntityModel<Cart> removeProductFromCart(
            @ApiParam(value = "Product id to remove", required = true) @PathVariable(value = "id") int id,
            @ApiParam(value = "quantity to remove", required = true) @RequestParam("qt") long qt) {
        log.info("removing a product to cart ...");
        EntityModel<Product> productResource = productController.readProduct(id);
        Optional.of(cart.getSelectedProducts())
                .filter(productMap -> productMap.containsKey(productResource.getContent()))
                .orElseThrow(() -> new CartMissingItemException(id));
        cartService.removeCartItems(cart, productResource.getContent(), qt);
        return cartResourceAssembler.toModel(cart);
    }
}
