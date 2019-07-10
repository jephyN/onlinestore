package com.bm.store.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@ApiModel(description = "All details about the catalague. ")
@Entity
@Data
@EqualsAndHashCode(callSuper = false) @Builder
public class Catalogue extends StoreUnit {

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
	@JoinTable(name = "CATALOG_PRODUCTS", joinColumns = @JoinColumn(name = "CATALOGUE_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	@ManyToMany
	@NotEmpty
	private Set<Product> catalogProducts;

}
