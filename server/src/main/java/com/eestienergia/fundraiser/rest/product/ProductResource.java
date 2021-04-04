package com.eestienergia.fundraiser.rest.product;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
class ProductResource {
    String code;
    String name;
    String avatarUrl;
    String description;
    long stock;
    BigDecimal price;
    String currencyCode;
}
