package com.eestienergia.fundraiser.domain.exception;

public class StockUpdateException extends RuntimeException {
    public StockUpdateException(int amount) {
        super("Cannot update stock to " + amount);
    }
}
