package com.bm.store.utils;

import com.bm.store.dto.representation.CartItem;
import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class Calculator {

    private final double tax;

    public Calculator(@Value("${utils.calculator.tax}") final double tax) {
        this.tax = tax;
    }

    /**
     * Calcule les taxes pour les articles du panier.
     *
     * @param cart le panier pour lequel les taxes seront calculées.
     */
    public void calculateTaxes(Cart cart) {
        cart.setTotalTaxes(calculateTotalFor(cart, getTaxableProductPrice(), isTaxableProduct()));
    }

    /**
     * Calcule le prix total du panier, taxes incluses.
     *
     * @param cart le panier pour lequel le prix total sera calculé.
     */
    public void calculateTotalPrice(Cart cart) {
        cart.setTotalPrice(calculateTotalFor(cart, getProductPrice(), isNotNullProduct())
                .add(cart.getTotalTaxes()));
    }

    /**
     * Calcule le total d'un ensemble d'articles du panier selon une fonction et un filtre.
     *
     * @param cart         le panier à traiter.
     * @param function     la fonction à appliquer à chaque article.
     * @param filter       le filtre pour sélectionner les articles à traiter.
     * @return le total des résultats de la fonction appliquée aux articles filtrés.
     */
    private BigDecimal calculateTotalFor(Cart cart, Function<CartItem, BigDecimal> function,
                                             Predicate<? super CartItem> filter) {
        return cart.getCartItems().stream()
                .filter(filter)
                .map(function)
                .parallel()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Obtient le prix d'un article du panier, taxes incluses.
     *
     * @return le prix de l'article avec les taxes.
     */
    private Function<CartItem, BigDecimal> getTaxableProductPrice() {
        return item -> getProduct(item).getPrice()
                .multiply(BigDecimal.valueOf(tax))
                .multiply(BigDecimal.valueOf(item.quantity()));
    }

    /**
     * Obtient le prix total avant taxes d'un ou plusieurs même articles du panier sans taxes.
     *
     * @return le prix total avant taxes d'un ou plusieurs même articles du panier sans taxes.
     */
    private Function<CartItem, BigDecimal> getProductPrice() {
        return item -> getProduct(item).getPrice()
                .multiply(BigDecimal.valueOf(item.quantity()));
    }

    /**
     * Vérifie si un article du panier est imposable.
     *
     * @return vrai si l'article est imposable, faux sinon.
     */
    private Predicate<? super CartItem> isTaxableProduct() {
        return cartItem -> getProduct(cartItem).isTaxable();
    }

    /**
     * Vérifie si un article du panier n'est pas nul.
     *
     * @return vrai si l'article n'est pas nul, faux sinon.
     */
    private Predicate<? super CartItem> isNotNullProduct() {
        return x -> true;
    }

    /**
     * Obtient le produit associé à un article du panier.
     *
     * @param item l'article du panier.
     * @return le produit associé à l'article.
     */
    private Product getProduct(CartItem item) {
        return item.product();
    }
}
