package com.eestienergia.fundraiser.rest.inventory;

import com.eestienergia.fundraiser.domain.InventoryRecord;
import org.springframework.stereotype.Component;

@Component
class InventoryRecordResourceConverter {
    public InventoryRecordResource convert(final InventoryRecord record) {
        return InventoryRecordResource.builder()
                .name(record.getName())
                .productCode(record.getProductCode())
                .stock(record.getStock())
                .build();
    }
}
