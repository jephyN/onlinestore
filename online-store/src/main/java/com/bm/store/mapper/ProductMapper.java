package com.bm.store.mapper;

import com.bm.store.dto.ProductModel;
import com.bm.store.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductModel mapProductToProductModel(Product product);
}
