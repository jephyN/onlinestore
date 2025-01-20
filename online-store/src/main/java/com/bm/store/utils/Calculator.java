package com.bm.store.utils;

import com.bm.store.dto.CartItem;
import com.bm.store.model.Cart;
import com.bm.store.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class Calculator {
    private static final double TAX = 0.13;

    public void calculateTaxes(Cart cart) {
        cart.setTotalTaxes(getTotal(cart,
                item -> getProduct(item).getPrice()
                            .multiply(BigDecimal.valueOf(TAX))
                            .multiply(BigDecimal.valueOf(item.getQuantity())),
                cartItem -> getProduct(cartItem).isTaxable())
                .doubleValue());
    }

    public void calculateTotalPrice(Cart cart) {
        cart.setTotalPrice(getTotal(cart,
                item -> getProduct(item).getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())),
                x -> true)
                .add(BigDecimal.valueOf(cart.getTotalTaxes()))
                .doubleValue());
    }

    private BigDecimal getTotal(Cart cart, final Function<CartItem, BigDecimal> function,
                               final Predicate<? super CartItem> filter) {
        return cart.getCartItems().stream()
                .filter(filter)
                .map(function)
                .parallel()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private Product getProduct(CartItem item) {
        return item.getProduct();
    }
}