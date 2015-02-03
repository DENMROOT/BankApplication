package com.luxoft.bankapp.model;

import com.luxoft.bankapp.annotations.NoDB;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public abstract class AbstractAccount implements Account, Serializable, BaseClassMarkerInterface{
    @NoDB
    private long accountId;

    protected float balance;

    private float initialOverdraft;

    protected float overdraft;

    private String accountType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractAccount)) {
            return false;
        }

        AbstractAccount that = (AbstractAccount) o;

        if (Float.compare(that.accountId, accountId) != 0) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        return (int) accountId;
    }

    @Override
    public float getBalance() {
        return balance;
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        this.accountId = Long.parseLong(feed.get("accountid"));
    }

    @Override
    public float decimalValue() {
        return Math.round(balance+overdraft);
    }

    @Override
    public long getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }
}
