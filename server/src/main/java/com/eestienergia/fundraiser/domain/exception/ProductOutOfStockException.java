package com.eestienergia.fundraiser.domain.exception;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(final String name) {
        super("Not enough " + name + " left in the stock");
    }
}
