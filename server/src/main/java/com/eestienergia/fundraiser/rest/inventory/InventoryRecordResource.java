package com.eestienergia.fundraiser.rest.inventory;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class InventoryRecordResource {
    String productCode;
    String name;
    int stock;
}
