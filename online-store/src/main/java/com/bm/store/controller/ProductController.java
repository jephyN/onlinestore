package com.bm.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bm.store.assembler.ProductResourceAssembler;
import com.bm.store.dao.ProductRepository;
import com.bm.store.exception.ProductNotFoundException;
import com.bm.store.model.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product")
@Api(value="Product Management")
@Slf4j
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductResourceAssembler assembler;
	
	@ApiOperation(value = "Read product's information")
	@GetMapping("/{id}")
	public EntityModel<Product> readProduct(@ApiParam(value = "Product id to read mission object", required = true) @PathVariable(value="id") long id){
		log.info("Reading a product ...");
		
		Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		
		return assembler.toModel(product);
	}
}
