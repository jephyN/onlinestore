package com.bm.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bm.store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
