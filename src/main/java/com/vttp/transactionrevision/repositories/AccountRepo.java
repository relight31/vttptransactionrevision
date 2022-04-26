package com.vttp.transactionrevision.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static com.vttp.transactionrevision.repositories.Queries.*;

@Repository
public class AccountRepo {

    @Autowired
    private JdbcTemplate template;

    public boolean deposit(String acctNo, double amount) {
        double balance = getBalance(acctNo);
        int result = template.update(SQL_UPDATE_ACCOUNT_BALANCE, balance + amount, acctNo);
        return result == 1;
    }

    public boolean withdraw(String acctNo, double amount) {
        double balance = getBalance(acctNo);
        int result = template.update(SQL_UPDATE_ACCOUNT_BALANCE,
                balance - amount,
                acctNo);
        return result == 1;
    }

    public double getBalance(String acctNo) {
        SqlRowSet result = template.queryForRowSet(SQL_READ_ACCOUNT_BALANCE, acctNo);
        if (result.next()) {
            return result.getDouble("balance");
        }
        return -1;
    }

    public boolean accountExists(String acctNo) {
        SqlRowSet result = template.queryForRowSet(SQL_CHECK_ACCOUNT_EXISTS, acctNo);
        return result.next();
    }
}
