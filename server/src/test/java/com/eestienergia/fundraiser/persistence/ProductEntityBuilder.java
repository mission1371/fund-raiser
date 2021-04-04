package com.eestienergia.fundraiser.persistence;

import java.math.BigDecimal;

public class ProductEntityBuilder extends ProductEntity.ProductEntityBuilder {

    public static ProductEntity.ProductEntityBuilder aProductEntity() {
        return ProductEntity.builder()
                .stock(10)
                .price(BigDecimal.ONE)
                .imageRelativePath("/path")
                .type(1L)
                .name("name")
                .code("code")
                .currencyCode("EUR");
    }
}