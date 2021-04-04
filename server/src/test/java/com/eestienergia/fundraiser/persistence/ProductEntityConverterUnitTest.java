package com.eestienergia.fundraiser.persistence;

import com.eestienergia.fundraiser.domain.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.eestienergia.fundraiser.persistence.ProductEntityBuilder.aProductEntity;
import static org.assertj.core.api.Assertions.assertThat;

class ProductEntityConverterUnitTest {

    private final ProductEntityConverter converter = new ProductEntityConverter();

    @Test
    void shouldConvert() {
        // given
        final ProductEntity entity = aProductEntity()
                .code("code")
                .name("name")
                .fileName("file-name")
                .stock(10)
                .price(BigDecimal.TEN)
                .currencyCode("currencyCode")
                .build();

        // when
        final Product product = converter.convert(entity);

        // then
        assertThat(product.getCode()).isEqualTo(entity.getCode());
        assertThat(product.getName()).isEqualTo(entity.getName());
        assertThat(product.getImageFileName()).isEqualTo(entity.getFileName());
        assertThat(product.getStock()).isEqualTo(entity.getStock());
        assertThat(product.getPrice()).isEqualTo(entity.getPrice());
        assertThat(product.getCurrencyCode()).isEqualTo(entity.getCurrencyCode());

    }
}
