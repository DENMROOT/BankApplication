package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.exceptions.OverDraftLimitExceededException;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class CheckingAccount extends AbstractAccount implements BaseClassMarkerInterface{

    public CheckingAccount (long accountId, float balance, float overdraft) {
        if (overdraft<0) {
            throw new IllegalArgumentException();
        }
        setAccountId(accountId);
        this.setInitialOverdraft(overdraft);
        this.setBalance(balance);
        this.overdraft=overdraft;
        setAccountType("C");
    }

    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public void deposit(float x) {
        setBalance(getBalance() + x);
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if (getBalance() + overdraft >= x) {
            setBalance(getBalance() - x);
        } else {
            throw new OverDraftLimitExceededException(this, getAvailableMoney());
        }
    }

    @Override
    public float getTotalAccountOverdraft() {
        return getBalance()>0?0:getBalance();
    }

    private float getAvailableMoney() {
        return getBalance()+overdraft;
    }

    @Override
    public void printReport() {
        System.out.println("Checking Account{" +
                "Account ID=" + getAccountId() +
                ",balance=" + getBalance() +
                ", overdraft=" + overdraft +
                '}');
    }

    @Override
    public String toString() {
        return "Checking Account{" +
                "Account ID=" + getAccountId() +
                ",balance=" + getBalance() +
                ", overdraft=" + overdraft +
                '}';
    }
}
