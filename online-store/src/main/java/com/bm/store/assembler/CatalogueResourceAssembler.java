package com.bm.store.assembler;

import com.bm.store.controller.CatalogueController;
import com.bm.store.dto.CatalogueModel;
import com.bm.store.dto.ProductModel;
import com.bm.store.model.Catalogue;
import com.bm.store.model.Product;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CatalogueResourceAssembler extends RepresentationModelAssemblerSupport<Catalogue, CatalogueModel> {

    private final ProductResourceAssembler productResourceAssembler;

    public CatalogueResourceAssembler(ProductResourceAssembler productResourceAssembler) {
        super(CatalogueController.class, CatalogueModel.class);
        this.productResourceAssembler = productResourceAssembler;
    }

    @Override
    public CatalogueModel toModel(Catalogue catalogue) {
        CatalogueModel catalogueModel = instantiateModel(catalogue);
        mapToCatalogueModel(catalogue, catalogueModel);
        addLink(catalogue, catalogueModel);
        return catalogueModel;
    }

    private void mapToCatalogueModel(Catalogue catalogue, CatalogueModel catalogueModel) {
        catalogueModel.setId(catalogue.getId());
        catalogueModel.setRegion(catalogue.getRegion());
        catalogueModel.setStartDate(catalogue.getStartDate());
        catalogueModel.setEndDate(catalogue.getEndDate());
        catalogueModel.setCatalogProducts(toProductModels(catalogue.getCatalogProducts()));
    }

    private Set<ProductModel> toProductModels(Set<Product> products){
        if(CollectionUtils.isEmpty(products)){
            return Collections.emptySet();
        }
        return products.stream()
                .map(productResourceAssembler::toModel)
                .collect(Collectors.toSet());
    }

    private void addLink(Catalogue catalogue, CatalogueModel catalogueModel) {
        catalogueModel.add(linkTo(methodOn(CatalogueController.class)
                .readCatalogue(catalogue.getId()))
                .withSelfRel());
    }
}
