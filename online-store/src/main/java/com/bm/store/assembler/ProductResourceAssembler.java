package com.bm.store.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.bm.store.controller.ProductController;
import com.bm.store.model.Product;

@Component
public class ProductResourceAssembler implements ResourceAssembler<Product, Resource<Product>> {

	@Override
	public Resource<Product> toResource(Product product) {
		return new Resource<>(product, linkTo(methodOn(ProductController.class)
				.readProduct(product.getId())).withSelfRel());
	}

}
