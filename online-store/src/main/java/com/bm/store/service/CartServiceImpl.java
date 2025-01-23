package com.bm.store.service;

import com.bm.store.model.Cart;
import com.bm.store.dto.CartItem;
import com.bm.store.model.Product;
import com.bm.store.utils.Calculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private final Calculator calculator;

    public CartServiceImpl(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void addCartItems(Cart cart, Product product, long quantity) {
        getSelectedProducts(cart).merge(product, quantity, Long::sum);
        generatesPricing(cart);
    }

    @Override
    public void removeCartItems(Cart cart, Product product, long quantity) {
        getSelectedProducts(cart).merge(product, quantity, (q1, q2) -> q1 - q2);
        if (getSelectedProducts(cart).get(product) <= 0L) {
            getSelectedProducts(cart).remove(product);
        }
        generatesPricing(cart);
    }

    private void generatesPricing(Cart cart) {
        manageCartItems(cart);
        calculator.calculateTaxes(cart);
        calculator.calculateTotalPrice(cart);
    }

    private void manageCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<>();
        getSelectedProducts(cart).forEach((product, quantity) -> {
            CartItem cartItem = CartItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .build();
            cartItems.add(cartItem);
        });
        cart.setCartItems(cartItems);
    }

    private Map<Product, Long> getSelectedProducts(Cart cart) {
        return cart.getSelectedProducts();
    }
}
