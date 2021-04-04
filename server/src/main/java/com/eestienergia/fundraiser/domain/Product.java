package com.eestienergia.fundraiser.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Product {
    String code;
    String name;
    String imageRelativePath;
    long stock;
    BigDecimal price;
    String currencyCode;
}
