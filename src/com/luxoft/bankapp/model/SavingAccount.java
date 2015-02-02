package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class SavingAccount extends AbstractAccount {

    public SavingAccount(long accountId, float x) {
        if (x<0) {
            throw new IllegalArgumentException();
        }
        this.setAccountId(accountId);
        this.balance=x;
        setAccountType("S");
    }


    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public void deposit(float x) {
        balance+=x;
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if (balance >= x) {
            balance -= x;
        } else {
            throw new NotEnoughFundsException(balance);
        }
    }

    @Override
    public float getTotalAccountOverdraft() {
        return 0;
    }

    @Override
    public void printReport() {
        System.out.println("Saving Account{" +
            "Account ID=" + getAccountId() +
            ",balance=" + balance +
            ", overdraft=" + overdraft +
            '}');
    }

    @Override
    public String toString() {
        return "Saving Account{" +
                "Account ID=" + getAccountId() +
                ",balance=" + balance +
                ", overdraft=" + overdraft +
                '}';
    }
}
