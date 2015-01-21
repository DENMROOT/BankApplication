package com.luxoft.bankapp.model;

import java.util.Map;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public interface Account extends Report {

    float getBalance();
    float getOverdraft();
    float deposit(float x);
    float withdraw(float x) throws NotEnoughFundsException;
    float decimalValue();
    float getTotalAccountOverdraft();
    public void parseFeed(Map<String, String> feed);
}
