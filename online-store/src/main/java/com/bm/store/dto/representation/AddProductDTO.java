package com.bm.store.dto.representation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO pour ajouter un article au panier")
public class AddProductDTO {

    @Schema(name = "quantity" , description = "La quantité à ajouter", minimum = "1")
    @Positive
    public long quantity;

    @Schema(name = "productId", description = "L'identifiant de l'article")
    public long productId;
}
