package com.bm.store.service;

import com.bm.store.dao.ProductRepository;
import com.bm.store.exception.ProductNotFoundException;
import com.bm.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "product_cache", key = "#id")
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}