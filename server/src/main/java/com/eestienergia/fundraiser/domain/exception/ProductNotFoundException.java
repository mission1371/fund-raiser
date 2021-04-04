package com.eestienergia.fundraiser.domain.exception;

import java.util.Set;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(final String code) {
        super("No product found with code: " + code);
    }

    public ProductNotFoundException(final Set<String> codes) {
        super("At least one product not found. " + codes);
    }
}
