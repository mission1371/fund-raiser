package com.eestienergia.fundraiser.domain;

import java.math.BigDecimal;

public class ProductBuilder extends Product.ProductBuilder {

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .code("code")
                .name("Muffin")
                .imageFileName("file-name")
                .stock(10)
                .price(BigDecimal.TEN)
                .currencyCode("EUR");
    }

}
