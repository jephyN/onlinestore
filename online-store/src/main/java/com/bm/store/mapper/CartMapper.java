package com.bm.store.mapper;

import com.bm.store.dto.CartModel;
import com.bm.store.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "cartItems", target = "cartItems", defaultExpression = "java(List.of())")
    CartModel mapCartToCartModel(Cart cart);
}
