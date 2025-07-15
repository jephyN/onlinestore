package com.bm.store.controller;

import com.bm.store.assembler.CartResourceAssembler;
import com.bm.store.dto.representation.AddProductDTO;
import com.bm.store.dto.representation.model.CartModel;
import com.bm.store.exception.CartMissingItemException;
import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import com.bm.store.service.CartService;
import com.bm.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management")
@Slf4j
public class CartController {

    private final CartResourceAssembler cartResourceAssembler;
    private final CartService cartService;
    private final ProductService productService;

    private final Map<String, Cart> carts = new ConcurrentHashMap<>();

    @Operation(summary = "Read the cart information")
    @GetMapping("/{userId}")
    public CartModel readCart(@PathVariable String userId) {
        log.info("Read a basket for the user {}...", userId);
        Cart cart = carts.computeIfAbsent(userId, k -> cartService.createCart());
        cartResourceAssembler.setUserId(userId);
        return cartResourceAssembler.toModel(cart);
    }

    @Operation(summary = "Add a product to the cart")
    @PatchMapping("/{userId}")
    public CartModel addProductToCart(
            @Valid @RequestBody AddProductDTO addProductDTO,
            @Parameter(name = "userId", required = true) @PathVariable String userId) {
        log.info("Add a product to the cart for the user {}...", userId);
        Product produit = productService.getProduct(addProductDTO.productId);
        Cart cart = carts.computeIfAbsent(userId, k -> cartService.createCart());
        cartResourceAssembler.setUserId(userId);
        cartService.addCartItems(cart, produit, addProductDTO.quantity);
        return cartResourceAssembler.toModel(cart);
    }

    @Operation(summary = "Remove a product from the cart")
    @DeleteMapping("/{userId}/product/{id}")
    public CartModel removeProductFromCart(
            @PathVariable String userId,
            @Parameter(description = "the id of the product to be deleted", required = true) @PathVariable(value = "id") long id,
            @Parameter(description = "the quantity to delete", required = true) @RequestParam("qt") long qt) {
        log.info("Removes a product from the cart for the user {}...", userId);
        Product product = productService.getProduct(id);
        Cart cart = carts.get(userId);
        if (cart != null) {
            validate(id, product, cart);
            cartResourceAssembler.setUserId(userId);
            cartService.removeCartItems(cart, product, qt);
            return cartResourceAssembler.toModel(cart);
        } else {
            throw new CartMissingItemException(id);
        }
    }

    private void validate(long id, Product product, Cart cart) {
        Optional.of(cart.getSelectedProducts())
                .filter(productMap -> productMap.containsKey(product))
                .orElseThrow(() -> new CartMissingItemException(id));
    }
}
