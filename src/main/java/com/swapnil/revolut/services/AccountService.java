package com.swapnil.revolut.services;

import com.swapnil.revolut.pojo.Account;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AccountService {
    private final Map<Long, Account> accountMap = new ConcurrentHashMap<>();
    private static final AtomicLong accountIdAtomicCounter = new AtomicLong(1);

    public Account createAccount(BigDecimal initialBalance) {
        Account account = new Account(accountIdAtomicCounter.getAndIncrement(), initialBalance);
        if (account.getWriteLock().tryLock()) {
            try {
                accountMap.put(account.getId(), account);
                account.getReadLock().lock();
            } finally {
                account.getWriteLock().unlock();
            }
        }

        Account readAccount = accountMap.get(account.getId());
        account.getReadLock().unlock();

        return readAccount;
    }

    public Optional<Account> getAccountById(Long id) {
        return Optional.ofNullable(accountMap.get(id));
    }
}
