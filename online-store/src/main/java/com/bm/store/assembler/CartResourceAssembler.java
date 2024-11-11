package com.bm.store.assembler;

import com.bm.store.controller.CartController;
import com.bm.store.controller.ProductController;
import com.bm.store.model.Cart;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartResourceAssembler implements RepresentationModelAssembler<Cart, EntityModel<Cart>> {

	@Override
	public EntityModel<Cart> toModel(Cart cart) {
		Set<Link> links = new LinkedHashSet<>();
		links.add(linkTo(methodOn(CartController.class).readCart()).withSelfRel());
		
		cart.getSelectedProducts().forEach((p,q) -> 
			links.add(linkTo(methodOn(ProductController.class).readProduct(p.getId())).withRel("products")));
		
		return new EntityModel<>(cart, links);
	}
}
