package com.eestienergia.fundraiser.rest.inventory;

import com.eestienergia.fundraiser.domain.InventoryRecord;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryRecordResourceConverterUnitTest {

    private final InventoryRecordResourceConverter converter = new InventoryRecordResourceConverter();

    @Test
    void shouldConvert() {
        // given
        final InventoryRecord record = InventoryRecord.builder()
                .productCode("productCode")
                .name("name")
                .stock(10)
                .build();

        // when
        final InventoryRecordResource resource = converter.convert(record);

        // then
        assertThat(resource.getProductCode()).isEqualTo(record.getProductCode());
        assertThat(resource.getName()).isEqualTo(record.getName());
        assertThat(resource.getStock()).isEqualTo(record.getStock());
    }
}