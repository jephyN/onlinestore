package com.bm.store.controller;

import com.bm.store.assembler.CatalogueResourceAssembler;
import com.bm.store.assembler.ProductResourceAssembler;
import com.bm.store.dao.CatalogueRepository;
import com.bm.store.exception.CatalogueNotFoundException;
import com.bm.store.model.Catalogue;
import com.bm.store.model.Product;
import com.bm.store.model.StoreUnit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogue")
@Api(value="Catalogue Management")
@Slf4j
public class CatalogueController {
	
	@Autowired
	private CatalogueRepository catalogueRepo;
	
	@Autowired
	private CatalogueResourceAssembler catalogueAssembler;
	
	@Autowired
	private ProductResourceAssembler productAssembler;

	@ApiOperation(value = "Read catalogue's information")
	@GetMapping("/{id}")
	public CollectionModel<EntityModel<? extends StoreUnit>> readCatalogue(@ApiParam(value = "Catalog id to read mission object", required = true) @PathVariable(value="id") int id){
		log.info("Reading a catalogue ...");
		Catalogue catalogue = catalogueRepo.findById(id).orElseThrow(() -> new CatalogueNotFoundException(id));
		List<EntityModel<? extends StoreUnit>> resources = new LinkedList<>();
		EntityModel<Catalogue> catalogueResource = catalogueAssembler.toModel(catalogue);
		resources.add(catalogueResource);
		List<EntityModel<Product>> productResources = catalogue.getCatalogProducts()
					.stream().map(productAssembler::toModel)
					.collect(Collectors.toList());
		resources.addAll(productResources);
		return new CollectionModel<>(resources, catalogueResource.getRequiredLink(IanaLinkRelations.SELF));
	}
}
