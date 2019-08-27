package com.swapnil.revolut.exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Insufficient Balance to transfer money");
    }
}
