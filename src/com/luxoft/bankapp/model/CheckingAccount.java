package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.exceptions.OverDraftLimitExceededException;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class CheckingAccount extends AbstractAccount{

    public CheckingAccount (long accountId, float balance, float overdraft) {
        if (overdraft<0) {
            throw new IllegalArgumentException();
        }
        setAccountId(accountId);
        this.initialOverdraft=overdraft;
        this.balance=balance;
        this.overdraft=overdraft;
    }

    /*
    @Override
    public float getBalance() {
        return balance-getTotalAccountOverdraft();
    }
    */

    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public void deposit(float x) {
        balance+=x;
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if (balance + overdraft >= x) {
            balance -= x;
        } else {
            throw new OverDraftLimitExceededException(this, getAvailableMoney());
        }
    }

    @Override
    public float getTotalAccountOverdraft() {
        return balance>0?0:balance;
    }

    private float getAvailableMoney() {
        return balance+overdraft;
    }

    @Override
    public void printReport() {
        System.out.println("Checking Account{" +
                "Account ID=" + getAccountId() +
                ",balance=" + balance +
                ", overdraft=" + overdraft +
                '}');
    }

    @Override
    public String toString() {
        return "Checking Account{" +
                "Account ID=" + getAccountId() +
                ",balance=" + balance +
                ", overdraft=" + overdraft +
                '}';
    }
}
