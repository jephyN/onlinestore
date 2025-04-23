package com.bm.store.mapper;

import com.bm.store.dto.representation.model.CatalogueModel;
import com.bm.store.model.Catalogue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CatalogueMapper {

    @Mapping(target = "catalogProducts", ignore = true)
    CatalogueModel mapCatalogueToCatalogueModel(Catalogue catalogue);
}
