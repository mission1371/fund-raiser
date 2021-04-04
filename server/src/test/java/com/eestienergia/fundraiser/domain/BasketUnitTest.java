package com.eestienergia.fundraiser.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.eestienergia.fundraiser.domain.ProductBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;

class BasketUnitTest {

    @Test
    void shouldAddProduct() {
        //given
        final Basket basket = new Basket();
        final Product product = aProduct().build();

        // when
        basket.add(product);

        // then
        assertThat(basket.getItems()).hasEntrySatisfying(product, count -> {
            assertThat(count).isEqualTo(1L);
        });
    }

    @Test
    void shouldIncreaseCountWhenSameProductAdded() {
        //given
        final Basket basket = new Basket();
        final Product product = aProduct().build();
        basket.add(product);

        // when
        basket.add(product);

        // then
        assertThat(basket.getItems()).hasEntrySatisfying(product, count -> {
            assertThat(count).isEqualTo(2L);
        });
    }

    @Test
    void shouldUpdateTotal() {
        //given
        final Basket basket = new Basket();
        final Product product = aProduct().price(BigDecimal.TEN).build();

        // when
        basket.add(product);
        basket.add(product);

        // then
        assertThat(basket.getTotal()).isEqualTo(BigDecimal.valueOf(20));
    }
}