package com.bm.store.assembler;

import com.bm.store.controller.ProductController;
import com.bm.store.dto.ProductModel;
import com.bm.store.mapper.ProductMapper;
import com.bm.store.model.Product;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductResourceAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {

    private final ProductMapper productMapper;

    public ProductResourceAssembler(ProductMapper productMapper) {
        super(ProductController.class, ProductModel.class);
        this.productMapper = productMapper;
    }

    @Override
    public ProductModel toModel(Product product) {
        ProductModel productModel = productMapper.mapProductToProductModel(product);
        addLink(product, productModel);
        return productModel;
    }

    private void addLink(Product product, ProductModel productModel) {
        productModel.add(linkTo(methodOn(ProductController.class)
                .readProduct(product.getId()))
                .withSelfRel());
    }
}
