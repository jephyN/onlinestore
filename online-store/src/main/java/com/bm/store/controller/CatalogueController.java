package com.bm.store.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Resources<Resource<? extends StoreUnit>> readCatalogue(@ApiParam(value = "Catalog id to read mission object", required = true) @PathVariable(value="id") int id){
		log.info("Reading a catalogue ...");
		
		Catalogue catalogue = catalogueRepo.findById(id).orElseThrow(() -> new CatalogueNotFoundException(id));
		
		List<Resource<? extends StoreUnit>> resources = new LinkedList<>();
		
		Resource<Catalogue> catalogueResource = catalogueAssembler.toResource(catalogue);
		resources.add(catalogueResource);
		List<Resource<Product>> productResources = catalogue.getCatalogProducts()
					.stream().map(productAssembler::toResource)
					.collect(Collectors.toList());
		resources.addAll(productResources);
		
		return new Resources<>(resources, catalogueResource.getId());
	}
}
