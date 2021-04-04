package com.eestienergia.fundraiser.rest.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class InventoryUpdateRequestResource {

    @NotBlank
    private String productCode;
    private Integer stock;
    private Integer addedStock;

}
