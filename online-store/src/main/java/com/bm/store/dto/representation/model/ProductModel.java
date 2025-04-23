package com.bm.store.dto.representation.model;

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
    private long id;

    @Schema(description = "The code of the product")
    private String productCode;

    @Schema(description = "The name of the product")
    private String name;

    @Schema(description = "The type of the product")
    private String productType;

    @Schema(description = "The description of the product")
    private String description;

    @Schema(description = "The URL of the product's image")
    private String imageUrl;

    @Schema(description = "The price of the product")
    private BigDecimal price;

    @Schema(description = "defines if the product is taxable")
    private boolean taxable;
}
