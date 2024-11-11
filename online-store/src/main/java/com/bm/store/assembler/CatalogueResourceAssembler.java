package com.bm.store.assembler;

import com.bm.store.controller.CatalogueController;
import com.bm.store.model.Catalogue;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CatalogueResourceAssembler implements RepresentationModelAssembler<Catalogue, EntityModel<Catalogue>> {

	@Override
	public EntityModel<Catalogue> toModel(Catalogue catalogue) {
		return new EntityModel<>(catalogue,linkTo(methodOn(CatalogueController.class)
				.readCatalogue(catalogue.getId())).withSelfRel());
	}

}
