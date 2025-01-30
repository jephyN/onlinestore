package com.bm.store.mapper;

import com.bm.store.dto.CartModel;
import com.bm.store.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "cartItems", target = "cartItems", defaultExpression = "java(List.of())")
    CartModel mapCartToCartModel(Cart cart);
}
