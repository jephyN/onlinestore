package com.bm.store.assembler;

import com.bm.store.controller.CatalogueController;
import com.bm.store.dto.representation.model.CatalogueModel;
import com.bm.store.dto.representation.model.ProductModel;
import com.bm.store.mapper.CatalogueMapper;
import com.bm.store.model.Catalogue;
import com.bm.store.model.Product;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CatalogueResourceAssembler extends RepresentationModelAssemblerSupport<Catalogue, CatalogueModel> {

    private final ProductResourceAssembler productResourceAssembler;
    private final CatalogueMapper catalogueMapper;

    public CatalogueResourceAssembler(CatalogueMapper catalogueMapper, ProductResourceAssembler productResourceAssembler) {
        super(CatalogueController.class, CatalogueModel.class);
        this.catalogueMapper = catalogueMapper;
        this.productResourceAssembler = productResourceAssembler;
    }

    @Override
    public CatalogueModel toModel(Catalogue catalogue) {
        CatalogueModel catalogueModel = catalogueMapper.mapCatalogueToCatalogueModel(catalogue);
        CollectionModel<ProductModel> catalogProducts = convertToProductModels(catalogue.getCatalogProducts());
        catalogueModel.setCatalogProducts(catalogProducts);
        addLink(catalogue, catalogueModel);
        return catalogueModel;
    }

    private CollectionModel<ProductModel> convertToProductModels(Set<Product> products){
        return productResourceAssembler.toCollectionModel(products);
    }

    private void addLink(Catalogue catalogue, CatalogueModel catalogueModel) {
        catalogueModel.add(linkTo(methodOn(CatalogueController.class)
                .readCatalogue(catalogue.getId()))
                .withSelfRel());
    }
}
