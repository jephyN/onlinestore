package com.bm.store.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.bm.store.controller.CatalogueController;
import com.bm.store.model.Catalogue;

@Component
public class CatalogueResourceAssembler implements ResourceAssembler<Catalogue, Resource<Catalogue>> {

	@Override
	public Resource<Catalogue> toResource(Catalogue catalogue) {
		return new Resource<>(catalogue,linkTo(methodOn(CatalogueController.class)
				.readCatalogue(catalogue.getId())).withSelfRel());
	}

}
