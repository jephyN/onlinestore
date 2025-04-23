package com.bm.store.assembler;

import com.bm.store.controller.CartController;
import com.bm.store.dto.representation.model.CartModel;
import com.bm.store.dto.representation.model.ProductModel;
import com.bm.store.mapper.CartMapper;
import com.bm.store.model.Cart;
import lombok.Setter;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartResourceAssembler extends RepresentationModelAssemblerSupport<Cart, CartModel> {

    private static final String PRODUCTS = "products";
    private final CartMapper cartMapper;
    private final ProductResourceAssembler productResourceAssembler;
    @Setter
    private String userId;

    public CartResourceAssembler(CartMapper cartMapper, ProductResourceAssembler productResourceAssembler) {
        super(CartController.class, CartModel.class);
        this.cartMapper = cartMapper;
        this.productResourceAssembler = productResourceAssembler;
    }

    @Override
    public CartModel toModel(Cart cart) {
        CartModel cartModel = cartMapper.mapCartToCartModel(cart);
        cartModel.add(linkTo(methodOn(CartController.class).readCart(userId)).withSelfRel());
        Set<Link> links = generateLinks(cartModel);
        cartModel.add(links);
        return cartModel;
    }

    private Set<Link> generateLinks(CartModel cartModel) {
        Set<Link> links = new LinkedHashSet<>();
        cartModel.getCartItems().stream()
                .parallel()
                .forEach(cartItem -> {
                    ProductModel productModel = productResourceAssembler.toModel(cartItem.product());
                    links.add(getProductLink(productModel));
                });
        return links;
    }

    private static Link getProductLink(ProductModel productModel) {
        return productModel.getRequiredLink(IanaLinkRelations.SELF)
                .withRel(LinkRelation.of(PRODUCTS))
                .withTitle(productModel.getProductCode())
                .withName(productModel.getName())
                .withType(productModel.getProductType());
    }
}
