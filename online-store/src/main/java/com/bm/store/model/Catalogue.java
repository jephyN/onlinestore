package com.bm.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

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
@Builder
public non-sealed class Catalogue extends StoreUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String region;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @JoinTable(name = "CATALOG_PRODUCTS", joinColumns = @JoinColumn(name = "CATALOGUE_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @ManyToMany
    private Set<Product> catalogProducts;

}
