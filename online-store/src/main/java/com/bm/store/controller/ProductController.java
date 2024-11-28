package com.bm.store.controller;

import com.bm.store.assembler.ProductResourceAssembler;
import com.bm.store.dao.ProductRepository;
import com.bm.store.dto.ProductModel;
import com.bm.store.exception.ProductNotFoundException;
import com.bm.store.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@Api(value = "Product Management")
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductResourceAssembler assembler;

    public ProductController(ProductRepository productRepository, ProductResourceAssembler assembler) {
        this.productRepository = productRepository;
        this.assembler = assembler;
    }

    @ApiOperation(value = "Read product's information")
    @GetMapping("/{id}")
    public ProductModel readProduct(@ApiParam(value = "Product id to read mission object", required = true) @PathVariable(value = "id") long id) {
        log.info("Reading a product ...");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return assembler.toModel(product);
    }
}
