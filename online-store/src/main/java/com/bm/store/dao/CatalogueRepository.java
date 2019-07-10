package com.bm.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bm.store.model.Catalogue;

public interface CatalogueRepository extends JpaRepository<Catalogue, Integer> {

}
