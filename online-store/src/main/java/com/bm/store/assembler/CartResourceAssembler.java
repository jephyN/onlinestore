package com.bm.store.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.bm.store.controller.CartController;
import com.bm.store.controller.ProductController;
import com.bm.store.model.Cart;

@Component
public class CartResourceAssembler implements ResourceAssembler<Cart, Resource<Cart>> {

	@Override
	public Resource<Cart> toResource(Cart cart) {
		Set<Link> links = new LinkedHashSet<>();
		links.add(linkTo(methodOn(CartController.class).readCart()).withSelfRel());
		
		cart.getSelectedProducts().forEach((p,q) -> {
			links.add(linkTo(methodOn(ProductController.class).readProduct(p.getId())).withRel("products"));
		});
		
		return new Resource<>(cart, links);
	}

}
