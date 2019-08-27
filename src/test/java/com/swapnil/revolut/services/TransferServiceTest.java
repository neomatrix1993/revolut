package com.swapnil.revolut.services;

import com.swapnil.revolut.exceptions.AccountNotFoundException;
import com.swapnil.revolut.exceptions.InsufficientBalanceException;
import com.swapnil.revolut.pojo.Account;
import com.swapnil.revolut.pojo.TransactionState;
import com.swapnil.revolut.pojo.TransferRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferServiceTest {

    private AccountService accountServiceMock = mock(AccountService.class);

    @Test(expected = AccountNotFoundException.class)
    @SuppressWarnings("unchecked")
    public void checkAccountNotFoundException() throws InsufficientBalanceException, AccountNotFoundException {
        TransferRequest transferRequest = new TransferRequest(1L, 2L, BigDecimal.TEN);
        TransferService transferService = new TransferService(accountServiceMock);

        when(accountServiceMock.getAccountById(transferRequest.getSourceAccountId()))
                .thenThrow(AccountNotFoundException.class);

        transferService.transfer(transferRequest);
    }

    @Test
    public void checkSameAccountAndSkipTransaction() throws InsufficientBalanceException, AccountNotFoundException {
        //given
        TransferRequest transferRequest = new TransferRequest(1L, 1L, BigDecimal.TEN);
        TransferService transferService = new TransferService(accountServiceMock);

        Account account = new Account(1L, BigDecimal.TEN);
        // when
        when(accountServiceMock.getAccountById(any())).thenReturn(Optional.of(account));
        TransactionState actualState = transferService.transfer(transferRequest);

        // then
        assertEquals(TransactionState.SAME_ACCOUNT, actualState);
    }

    @Test
    public void checkSuccessfulTransfer() throws InsufficientBalanceException, AccountNotFoundException {
        //given
        TransferRequest transferRequest = new TransferRequest(1L, 2L, BigDecimal.TEN);
        TransferService transferService = new TransferService(accountServiceMock);

        Account sourceAccount = new Account(1L, BigDecimal.TEN);
        Account destAccount = new Account(2L, BigDecimal.TEN);

        // when
        when(accountServiceMock.getAccountById(transferRequest.getSourceAccountId()))
                .thenReturn(Optional.of(sourceAccount));
        when(accountServiceMock.getAccountById(transferRequest.getDestAccountId()))
                .thenReturn(Optional.of(destAccount));
        TransactionState actualState = transferService.transfer(transferRequest);

        //then
        assertEquals(TransactionState.SUCCESS, actualState);
    }
}