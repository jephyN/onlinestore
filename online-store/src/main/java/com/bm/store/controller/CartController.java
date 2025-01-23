package com.bm.store.controller;

import com.bm.store.assembler.CartResourceAssembler;
import com.bm.store.dto.CartModel;
import com.bm.store.exception.CartMissingItemException;
import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import com.bm.store.service.CartService;
import com.bm.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management")
@Slf4j
public class CartController {

    @Resource(name = "customerCart")
    private Cart cart;
    private final CartResourceAssembler cartResourceAssembler;
    private final CartService cartService;
    private final ProductService productService;

    @Operation(summary = "Read cart's information")
    @GetMapping
    public CartModel readCart() {
        log.info("Reading a cart ...");
        return cartResourceAssembler.toModel(cart);
    }

    @Operation(summary = "Add a product in the cart")
    @PatchMapping("/product/{id}")
    public CartModel addProductToCart(
            @Parameter(name = "Product id to add", required = true) @PathVariable(value = "id") long id,
            @Parameter(name = "quantity to add", required = true) @RequestParam("qt") long qt) {
        log.info("adding a product to cart ...");
        Product product = productService.getProduct(id);
        cartService.addCartItems(cart, product, qt);
        return cartResourceAssembler.toModel(cart);
    }

    @Operation(summary = "Remove a product from cart")
    @DeleteMapping("/product/{id}")
    public CartModel removeProductFromCart(
            @Parameter(name = "Product id to remove", required = true) @PathVariable(value = "id") long id,
            @Parameter(name = "quantity to remove", required = true) @RequestParam("qt") long qt) {
        log.info("removing a product to cart ...");
        Product product = productService.getProduct(id);
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
