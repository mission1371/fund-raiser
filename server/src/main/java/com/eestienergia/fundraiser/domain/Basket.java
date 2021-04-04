package com.eestienergia.fundraiser.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Basket {

    private final Map<Product, Long> items = new HashMap<>();
    private BigDecimal total = BigDecimal.ZERO;

    public void add(final Product product) {
        if (items.containsKey(product)) {
            items.replace(product, items.get(product) + 1);
        } else {
            items.put(product, 1L);
        }
        total = total.add(product.getPrice());
    }

}
