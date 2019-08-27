package com.swapnil.revolut.exceptions;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String id) {
        super("Account not found for id: " + id);
    }
}
