package com.eestienergia.fundraiser.domain;

import org.junit.jupiter.api.Test;

import static com.eestienergia.fundraiser.domain.ProductBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;

class InventoryRecordUnitTest {

    @Test
    void shouldConstructUsingProduct() {
        // given
        final Product product = aProduct()
                .name("product-name")
                .code("product-code")
                .stock(15)
                .build();

        // when
        final InventoryRecord inventoryRecord = InventoryRecord.of(product);

        // then
        assertThat(inventoryRecord.getProductCode()).isEqualTo("product-code");
        assertThat(inventoryRecord.getName()).isEqualTo("product-name");
        assertThat(inventoryRecord.getStock()).isEqualTo(15);
    }
}