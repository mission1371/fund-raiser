package com.eestienergia.fundraiser.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InventoryRecord {

    String productCode;
    String name;
    int stock;

    public static InventoryRecord of(final Product product) {
        return InventoryRecord.builder()
                .name(product.getName())
                .productCode(product.getCode())
                .stock(product.getStock())
                .build();
    }

}
