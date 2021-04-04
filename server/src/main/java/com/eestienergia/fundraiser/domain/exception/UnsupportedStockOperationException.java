package com.eestienergia.fundraiser.domain.exception;

public class UnsupportedStockOperationException extends RuntimeException {
    public UnsupportedStockOperationException() {
        super("Unsupported stock operation!");
    }
}
