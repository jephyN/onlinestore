package com.bm.store.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@ApiModel(description = "All details about the catalague. ")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CatalogueModel extends RepresentationModel<CatalogueModel> {

    @ApiModelProperty(notes = "The database generated catalogue ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(notes = "The region of the catalogue")
    private String region;

    @ApiModelProperty(notes = "the start date of the catalogue")
    @NotNull
    private LocalDate startDate;

    @ApiModelProperty(notes = "the end date of the catalogue")
    @NotNull
    private LocalDate endDate;

    @ApiModelProperty(notes = "the products of the catalogue")
    @NotEmpty
    private Set<ProductModel> catalogProducts;

}
