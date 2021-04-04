package com.eestienergia.fundraiser.domain.service;

import com.eestienergia.fundraiser.domain.InventoryRecord;
import com.eestienergia.fundraiser.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.eestienergia.fundraiser.domain.ProductBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InventoryServiceUnitTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private InventoryService service;

    @Test
    void shouldLoadAllProducts() {
        // given
        final Product givenProduct = aProduct().build();
        final InventoryRecord givenRecord = InventoryRecord.of(givenProduct);
        given(productService.getProducts()).willReturn(Collections.singletonList(givenProduct));

        // when
        final List<InventoryRecord> records = service.fetchInventory();

        // then
        assertThat(records).containsExactly(givenRecord);
    }

    @Test
    void shouldIncreaseStockOfGivenProduct() {
        // given
        final long givenAmount = 10;
        final String givenCode = "CP10";
        final Product givenProduct = aProduct().build();
        final Product givenUpdatedProduct = aProduct().build();
        final InventoryRecord givenRecord = InventoryRecord.of(givenUpdatedProduct);
        given(productService.getByCode(eq(givenCode))).willReturn(givenProduct);
        given(productService.increaseStock(eq(givenProduct), eq(givenAmount))).willReturn(givenUpdatedProduct);

        // when
        final InventoryRecord record = service.increaseStock(givenCode, givenAmount);

        // then
        assertThat(record).isEqualTo(givenRecord);
        verify(productService).increaseStock(givenProduct, givenAmount);
    }

    @Test
    void shouldDUpdateStockOfGivenProduct() {
        // given
        final long givenAmount = 10;
        final String givenCode = "CP10";
        final Product givenProduct = aProduct().build();
        final Product givenUpdatedProduct = aProduct().build();
        final InventoryRecord givenRecord = InventoryRecord.of(givenUpdatedProduct);
        given(productService.getByCode(eq(givenCode))).willReturn(givenProduct);
        given(productService.updateStock(eq(givenProduct), eq(givenAmount))).willReturn(givenUpdatedProduct);

        // when
        final InventoryRecord record = service.updateStock(givenCode, givenAmount);

        // then
        assertThat(record).isEqualTo(givenRecord);
        verify(productService).updateStock(givenProduct, givenAmount);
    }
}