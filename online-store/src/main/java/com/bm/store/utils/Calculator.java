package com.bm.store.utils;

import com.bm.store.model.Cart;
import com.bm.store.model.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

@Component
public class Calculator {
    private static final double TAX = 0.13;

    public void calculateTaxes(Cart cart) {
        cart.setTotalTaxes(getTotal(cart,
                item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(TAX))
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .doubleValue());
    }

    public void calculateTotalPrice(Cart cart) {
        cart.setTotalPrice(getTotal(cart,
                item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .add(BigDecimal.valueOf(cart.getTotalTaxes()))
                .doubleValue());
    }

    public BigDecimal getTotal(Cart cart, final Function<CartItem, BigDecimal> function) {
        return cart.getCartItems().stream()
                .map(function)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}