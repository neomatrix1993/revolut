package com.swapnil.revolut.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swapnil.revolut.exceptions.ConcurrencyException;
import com.swapnil.revolut.exceptions.InsufficientBalanceException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Setter
@EqualsAndHashCode
public class Account {
    Long id;
    BigDecimal balance;
    @JsonIgnore
    ReentrantReadWriteLock lock;
    @JsonIgnore
    ReentrantReadWriteLock.ReadLock readLock;
    @JsonIgnore
    ReentrantReadWriteLock.WriteLock writeLock;

    public Account(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
        this.lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    private void validateBalance(BigDecimal amount) throws InsufficientBalanceException {
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }
    }

    /**
     * Algorithm() {
     * Take ReadLock Source()
     * readAndValidateBalance()
     * Release ReadLock Source()
     * <p>
     * Take WriteLock Source()
     * Take WriteLock Destination()
     * <p>
     * readAndValidateBalance() again because another thread could have taken write lock too.
     * Make the transfer
     * <p>
     * Release WriteLock Destination()
     * Release WriteLock Source()
     *
     * PS: We can use timeout as well, or throw exception to client. Use case basis.
     * }
     *
     * @param destAccount Destination account
     * @param amount      Amount
     * @throws InsufficientBalanceException Insufficient balance exception
     */
    public void transferAmountTo(Account destAccount, BigDecimal amount) throws InsufficientBalanceException {

        // start source with a read lock to read the balance
        if (this.readLock.tryLock()) {
            try {
                this.validateBalance(amount);
            } finally {
                this.readLock.unlock();
            }
            // upgrade source to write to debit them
            if (this.writeLock.tryLock()) {
                try {
                    // acquire dest object write lock to credit them
                    if (destAccount.writeLock.tryLock()) {
                        try {
                            this.validateBalance(amount);
                            // debit from source, and credit to dest
                            destAccount.setBalance(destAccount.getBalance().add(amount));
                            this.setBalance(this.getBalance().subtract(amount));
                        } finally {
                            destAccount.writeLock.unlock();
                        }
                    }
                } finally {
                    this.writeLock.unlock();
                }
            } else {
                throw new ConcurrencyException();
            }

        } else {
            throw new ConcurrencyException();
        }
    }
}