package com.bm.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author Jephy
 * @version 1.0
 * @since 2019-07-07
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Product extends StoreUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    protected long id;

    @NotBlank
    protected String productCode;

    @NotBlank
    protected String name;

    @NotBlank
    protected String productType;

    protected String description;

    protected String imageUrl;

    @NotNull
    protected BigDecimal price;

    protected boolean isTaxable;
}
