package com.bm.store.service;

import org.springframework.stereotype.Service;

import com.bm.store.model.Cart;
import com.bm.store.model.Product;

@Service
public interface CartService {
	
	void addCartItems(Cart cart, Product product, long quantity);
	
	void removeCartItems(Cart cart, Product product, long quantity);
}
