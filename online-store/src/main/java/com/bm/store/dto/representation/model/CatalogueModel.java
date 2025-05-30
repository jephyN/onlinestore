package com.bm.store.dto.representation.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@Schema(description = "All details about the catalogue.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CatalogueModel extends RepresentationModel<CatalogueModel> {

    @Schema(description = "The database generated catalogue ID")
    private int id;

    @Schema(description = "The region of the catalogue")
    private String region;

    @Schema(description = "the start date of the catalogue")
    private LocalDate startDate;

    @Schema(description = "the end date of the catalogue")
    private LocalDate endDate;

    @Schema(description = "the products of the catalogue")
    private CollectionModel<ProductModel> catalogProducts;

}
