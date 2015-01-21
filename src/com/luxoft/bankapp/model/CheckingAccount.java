package com.luxoft.bankapp.model;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class CheckingAccount extends AbstractAccount{

    public CheckingAccount (float x) {
        if (x<0) {
            throw new IllegalArgumentException();
        }
        initialOverdraft=x;
        overdraft=x;
    }



    @Override
    public float getBalance() {
        return balance-getTotalAccountOverdraft();
    }

    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public float deposit(float x) {
        return 0;
    }

    @Override
    public float withdraw(float x) throws NotEnoughFundsException, OverDraftLimitExceededException {

        if (x > overdraft) {
            throw new OverDraftLimitExceededException(this, getAvailableMoney());
        }
        if (x >(balance+overdraft)) {
            throw new NotEnoughFundsException(balance+overdraft);
        }
        if (x > balance) {
            overdraft-=x-balance;
            balance=0;
        }
        return 0;
    }

    @Override
    public float getTotalAccountOverdraft() {
        if (initialOverdraft == overdraft) {
            return 0;
        }
        return initialOverdraft - overdraft;
    }

    private Float getAvailableMoney() {
        return balance+overdraft;
    }

    @Override
    public void printReport() {

    }

    @Override
    public String toString() {
        return "Checking Account{" +
                "balance=" + balance +
                ", overdraft=" + overdraft +
                '}';
    }
}
