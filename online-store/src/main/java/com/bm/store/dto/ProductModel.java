package com.bm.store.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@Getter
@Setter
@ToString
@Relation(collectionRelation = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ProductModel extends RepresentationModel<ProductModel> {

    @ApiModelProperty(notes = "The database generated product ID")
    protected long id;

    @ApiModelProperty(notes = "The code of the product")
    protected String productCode;

    @ApiModelProperty(notes = "The name of the product")
    protected String name;

    @ApiModelProperty(notes = "The type of the product")
    protected String productType;

    @ApiModelProperty(notes = "The description of the product")
    protected String description;

    @ApiModelProperty(notes = "The URL of the product's image")
    protected String imageUrl;

    @ApiModelProperty(notes = "The price of the product")
    protected BigDecimal price;

    @ApiModelProperty(notes = "defines if the product is taxable")
    protected boolean isTaxable;
}
