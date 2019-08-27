package com.swapnil.revolut.exceptions;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException() {
        super("Couldn't acquire lock on resource, try again.");
    }
}
