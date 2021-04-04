package com.eestienergia.fundraiser.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class ProductCode {
    long code;
}
