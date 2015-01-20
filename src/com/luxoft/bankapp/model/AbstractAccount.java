package com.luxoft.bankapp.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public abstract class AbstractAccount implements Account, Serializable{
    private long accountId;
    protected float balance;
    protected float initialOverdraft;
    protected float overdraft;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractAccount)) return false;

        AbstractAccount that = (AbstractAccount) o;

        if (Float.compare(that.accountId, accountId) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {

        return (int) accountId;
    }

    @Override
    public String toString() {
        return "AbstractAccount{" +
                "balance=" + balance +
                ", overdraft=" + overdraft +
                '}';
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        this.setAccountId(Long.parseLong(feed.get("accountid")));
        //this.setOverdraft(Float.parseFloat(feed.get("overdraft")));
        //this.setBalance(Float.parseFloat(feed.get("balance")));
    }

    @Override
    public float decimalValue() {
        return Math.round(balance+overdraft);
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
