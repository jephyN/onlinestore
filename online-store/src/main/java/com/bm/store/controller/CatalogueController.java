package com.bm.store.controller;

import com.bm.store.assembler.CatalogueResourceAssembler;
import com.bm.store.dao.CatalogueRepository;
import com.bm.store.dto.CatalogueModel;
import com.bm.store.exception.CatalogueNotFoundException;
import com.bm.store.model.Catalogue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalogue")
@Api(value = "Catalogue Management")
@Slf4j
public class CatalogueController {

    private final CatalogueRepository catalogueRepo;
    private final CatalogueResourceAssembler catalogueAssembler;

    public CatalogueController(CatalogueRepository catalogueRepo,
                               CatalogueResourceAssembler catalogueAssembler) {
        this.catalogueRepo = catalogueRepo;
        this.catalogueAssembler = catalogueAssembler;
    }

    @ApiOperation(value = "Read catalogue's information")
    @GetMapping("/{id}")
    public CatalogueModel readCatalogue(@ApiParam(value = "Catalog id to read mission object", required = true) @PathVariable(value = "id") int id) {
        log.info("Reading a catalogue ...");
        Catalogue catalogue = catalogueRepo.findById(id)
                .orElseThrow(() -> new CatalogueNotFoundException(id));
        return catalogueAssembler.toModel(catalogue);
    }
}
