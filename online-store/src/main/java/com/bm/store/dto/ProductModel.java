package com.bm.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@Schema(description = "All details about the product.")
@Getter
@Setter
@ToString
@Relation(collectionRelation = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ProductModel extends RepresentationModel<ProductModel> {

    @Schema(description = "The database generated product ID")
    protected long id;

    @Schema(description = "The code of the product")
    protected String productCode;

    @Schema(description = "The name of the product")
    protected String name;

    @Schema(description = "The type of the product")
    protected String productType;

    @Schema(description = "The description of the product")
    protected String description;

    @Schema(description = "The URL of the product's image")
    protected String imageUrl;

    @Schema(description = "The price of the product")
    protected BigDecimal price;

    @Schema(description = "defines if the product is taxable")
    protected boolean isTaxable;
}
