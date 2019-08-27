package com.swapnil.revolut.services;

import com.swapnil.revolut.exceptions.AccountNotFoundException;
import com.swapnil.revolut.exceptions.InsufficientBalanceException;
import com.swapnil.revolut.pojo.Account;
import com.swapnil.revolut.pojo.TransactionState;
import com.swapnil.revolut.pojo.TransferRequest;


public class TransferService {

    private AccountService accountService;

    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    public TransactionState transfer(TransferRequest request) throws
            AccountNotFoundException, InsufficientBalanceException {
        // check if both accounts exist
        TransactionState txState;
        Account sourceAccount = accountService.getAccountById(request.getSourceAccountId())
                .orElseThrow(() -> new AccountNotFoundException(request.getSourceAccountId().toString()));
        Account destAccount = accountService.getAccountById(request.getDestAccountId())
                .orElseThrow(() -> new AccountNotFoundException(request.getDestAccountId().toString()));

        if (sourceAccount.getId().equals(destAccount.getId())) {
            return TransactionState.SAME_ACCOUNT;
        }

        sourceAccount.transferAmountTo(destAccount, request.getAmount());
        txState = TransactionState.SUCCESS;

        return txState;
    }
}