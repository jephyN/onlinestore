package com.bm.store.service;

import com.bm.store.dao.CatalogueRepository;
import com.bm.store.exception.CatalogueNotFoundException;
import com.bm.store.model.Catalogue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CatalogueService {

    private final CatalogueRepository catalogueRepository;

    public Catalogue getCatalogue(int id) {
        return catalogueRepository.findById(id)
                .orElseThrow(() -> new CatalogueNotFoundException(id));
    }
}
