package com.bm.store.assembler;

import com.bm.store.controller.ProductController;
import com.bm.store.dto.ProductModel;
import com.bm.store.model.Product;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductResourceAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {

    public ProductResourceAssembler() {
        super(ProductController.class, ProductModel.class);
    }

    @Override
    public ProductModel toModel(Product product) {
        ProductModel productModel = instantiateModel(product);
        mapToProductModel(product, productModel);
        addLink(product, productModel);
        return productModel;
    }

    private void mapToProductModel(Product product, ProductModel productModel) {
        productModel.setId(product.getId());
        productModel.setName(product.getName());
        productModel.setDescription(product.getDescription());
        productModel.setPrice(product.getPrice());
        productModel.setProductCode(product.getProductCode());
        productModel.setProductType(product.getProductType());
        productModel.setImageUrl(product.getImageUrl());
        productModel.setPrice(product.getPrice());
        productModel.setTaxable(product.isTaxable());
    }

    private void addLink(Product product, ProductModel productModel) {
        productModel.add(linkTo(methodOn(ProductController.class)
                .readProduct(product.getId()))
                .withSelfRel());
    }
}
