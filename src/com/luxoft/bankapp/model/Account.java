package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;

import java.util.Map;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public interface Account extends Report {

    public float getBalance();
    public float getOverdraft();
    public void deposit(float x);
    public void withdraw(float x) throws NotEnoughFundsException;
    public float decimalValue();
    public float getTotalAccountOverdraft();
    public void parseFeed(Map<String, String> feed);
    public long getAccountId();
    public void setAccountId(long accountId);
    public String getAccountType();
    public float getInitialOverdraft();
    void setBalance(float balance);
}
