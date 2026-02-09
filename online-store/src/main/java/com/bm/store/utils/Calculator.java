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
     * Calculate the taxes for the items in the basket.
     *
     * @param cart the basket for which taxes will be calculated.
     */
    public void calculateTaxes(Cart cart) {
        BigDecimal totalTaxes = calculateTotalFor(cart, getTaxableProductPrice(), isTaxableProduct());
        cart.setTotalTaxes(totalTaxes);
    }

    /**
     * Calculate the total price of the basket, taxes included.
     *
     * @param cart the basket for which the total price will be calculated.
     */
    public void calculateTotalPrice(Cart cart) {
        BigDecimal totalPriceWithoutTaxes = calculateTotalFor(cart, getProductPrice(), isNotNullProduct());
        BigDecimal totalPrice = totalPriceWithoutTaxes.add(cart.getTotalTaxes());
        cart.setTotalPrice(totalPrice);
    }

    /**
     * Calculates the total of a set of items in the basket according to a function and a filter.
     *
     * @param cart         the basket to process.
     * @param function     the function to apply to each article.
     * @param filter       the filter to select the items to be processed.
     * @return the total results of the function applied to the filtered articles.
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
     * Get the price of an item from the basket, taxes included.
     *
     * @return the price of the item with taxes.
     */
    private Function<CartItem, BigDecimal> getTaxableProductPrice() {
        return item -> getProduct(item).getPrice()
                .multiply(BigDecimal.valueOf(tax))
                .multiply(BigDecimal.valueOf(item.quantity()));
    }

    /**
     * Obtains the total price before taxes of one or more same items in the basket without taxes.
     *
     * @return the total price before taxes of one or more same items in the basket without taxes.
     */
    private Function<CartItem, BigDecimal> getProductPrice() {
        return item -> getProduct(item).getPrice()
                .multiply(BigDecimal.valueOf(item.quantity()));
    }

    /**
     * Checks if an item in the basket is taxable.
     *
     * @return true if the item is taxable, false otherwise.
     */
    private Predicate<? super CartItem> isTaxableProduct() {
        return cartItem -> getProduct(cartItem).isTaxable();
    }

    /**
     * Checks if an item in the cart is not null.
     *
     * @return true if the article is not null, false otherwise.
     */
    private Predicate<? super CartItem> isNotNullProduct() {
        return x -> true;
    }

    /**
     * Gets the product associated with an item in the cart.
     *
     * @param item the item from the basket.
     * @return the product associated with the item.
     */
    private Product getProduct(CartItem item) {
        return item.product();
    }
}
