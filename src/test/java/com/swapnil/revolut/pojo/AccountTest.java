package com.swapnil.revolut.pojo;

import com.swapnil.revolut.exceptions.InsufficientBalanceException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    @Test
    public void checkDepositAmountToWorkingFlow() throws InsufficientBalanceException {
        Account sourceAccount = new Account(1L, BigDecimal.TEN);
        Account destAccount = new Account(2L, BigDecimal.TEN);

        sourceAccount.transferAmountTo(destAccount, BigDecimal.TEN);

        assertEquals(BigDecimal.ZERO, sourceAccount.getBalance());
        assertEquals(BigDecimal.valueOf(20L), destAccount.getBalance());
    }

    @Test(expected = InsufficientBalanceException.class)
    public void checkInsufficientException() throws InsufficientBalanceException {
        Account sourceAccount = new Account(1L, BigDecimal.TEN);
        Account destAccount = new Account(2L, BigDecimal.TEN);

        sourceAccount.transferAmountTo(destAccount, BigDecimal.valueOf(100));
    }
}