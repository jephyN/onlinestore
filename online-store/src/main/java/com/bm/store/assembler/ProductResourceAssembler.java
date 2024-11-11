package com.bm.store.assembler;

import com.bm.store.controller.ProductController;
import com.bm.store.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductResourceAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

	@Override
	public EntityModel<Product> toModel(Product product) {
		return new EntityModel<>(product, linkTo(methodOn(ProductController.class)
				.readProduct(product.getId())).withSelfRel());
	}

}
