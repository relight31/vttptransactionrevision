package com.vttp.transactionrevision.repositories;

public interface Queries {
    public static final String SQL_UPDATE_ACCOUNT_BALANCE = "update account set balance = ? where acct_id = ?";
    public static final String SQL_READ_ACCOUNT_BALANCE = "select balance from account where acct_id = ?";
    public static final String SQL_CHECK_ACCOUNT_EXISTS = SQL_READ_ACCOUNT_BALANCE;
}
