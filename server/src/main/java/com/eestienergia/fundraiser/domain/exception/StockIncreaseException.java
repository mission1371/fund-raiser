package com.eestienergia.fundraiser.domain.exception;

public class StockIncreaseException extends RuntimeException {
    public StockIncreaseException(long amount) {
        super("Cannot increase stock by " + amount);
    }
}
