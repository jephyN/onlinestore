package com.bm.store.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Product extends StoreUnit {

	@ApiModelProperty(notes = "The database generated product ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	protected long id;

	@ApiModelProperty(notes = "The code of the product")
	@NotBlank
	protected String productCode;

	@ApiModelProperty(notes = "The name of the product")
	@NotBlank
	protected String name;

	@ApiModelProperty(notes = "The type of the product")
	@NotBlank
	protected String productType;

	@ApiModelProperty(notes = "The description of the product")
	protected String description;

	@ApiModelProperty(notes = "The URL of the product's image")
	protected String imageUrl;

	@ApiModelProperty(notes = "The price of the product")
	@NotNull
	protected BigDecimal price;
	
	@ApiModelProperty(notes = "defines if the product is taxable")
	protected boolean isTaxable;
}
