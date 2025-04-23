package com.bm.store.controller;

import com.bm.store.assembler.ProductResourceAssembler;
import com.bm.store.dto.representation.model.ProductModel;
import com.bm.store.model.Product;
import com.bm.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
@Tag(name = "Product Management")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductResourceAssembler productResourceAssembler;

    @Operation(summary = "Read product's information")
    @GetMapping("/{id}")
    public ProductModel readProduct(@Parameter(name = "Product id to read mission object", required = true) @PathVariable(value = "id") long id) {
        log.info("Reading a product ...");
        Product product = productService.getProduct(id);
        return productResourceAssembler.toModel(product);
    }
}
