package com.vttp.transactionrevision.services;

import com.vttp.transactionrevision.repositories.AccountRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepo repository;

    @Transactional
    public void transfer(String toAcct, String fromAcct, double amount) {
        // accounts do not exist
        if (!repository.accountExists(toAcct) ||
                !repository.accountExists(fromAcct)) {
            throw new IllegalArgumentException("Account does not exist.");
        }
        // invalid balance / update amount
        if (repository.getBalance(fromAcct) < 0 || amount < 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        // not enough to transfer
        if (repository.getBalance(fromAcct) < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        }
        // actual operation
        if (!(repository.withdraw(fromAcct, amount) &&
                repository.deposit(toAcct, amount))) {
            throw new RuntimeException("Cannot perform transfer");
            // rollback
        }
    }
}
