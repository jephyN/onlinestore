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

    // Utilisation de ConcurrentHashMap pour la gestion des paniers
    private final Map<String, Cart> carts = new ConcurrentHashMap<>();

    @Operation(summary = "Lire les informations du panier")
    @GetMapping("/{userId}")
    public CartModel readCart(@PathVariable String userId) {
        log.info("Lis un panier pour l'utilisateur {}...", userId);
        Cart cart = carts.computeIfAbsent(userId, k -> cartService.createCart());
        cartResourceAssembler.setUserId(userId);
        return cartResourceAssembler.toModel(cart);
    }

    @Operation(summary = "Ajoute un produit au panier")
    @PatchMapping("/{userId}")
    public CartModel addProductToCart(
            @Valid @RequestBody AddProductDTO addProductDTO,
            @Parameter(name = "l'utilisateur", required = true) @PathVariable String userId) {
        log.info("Ajoute un produit au panier pour l'utilisateur {}...", userId);
        Product produit = productService.getProduct(addProductDTO.productId);
        Cart cart = carts.computeIfAbsent(userId, k -> cartService.createCart());
        cartResourceAssembler.setUserId(userId);
        cartService.addCartItems(cart, produit, addProductDTO.quantity);
        return cartResourceAssembler.toModel(cart);
    }

    @Operation(summary = "Supprime un produit du panier")
    @DeleteMapping("/{userId}/product/{id}")
    public CartModel removeProductFromCart(
            @PathVariable String userId,
            @Parameter(name = "l'id du produit à supprimer", required = true) @PathVariable(value = "id") long id,
            @Parameter(name = "quantité à supprimer", required = true) @RequestParam("qt") long qt) {
        log.info("Supprime un produit du panier pour l'utilisateur {}...", userId);
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
