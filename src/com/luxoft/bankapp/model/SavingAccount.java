package com.luxoft.bankapp.model;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class SavingAccount extends AbstractAccount {

    public SavingAccount(float x) {
        if (x<0) {
            throw new IllegalArgumentException();
        }
        balance=x;
    }


    @Override
    public float getBalance() {
        return balance;
    }

    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public float deposit(float x) {
        balance+=x;
        return 0;
    }

    @Override
    public float withdraw(float x) throws NotEnoughFundsException {
        if (x>balance) {
            throw new NotEnoughFundsException(balance);
        }
        if (x <= balance) {
            balance-=x;
            overdraft=0;
        }
        return 0;
    }

    @Override
    public float getTotalAccountOverdraft() {
        return 0;
    }

    @Override
    public void printReport() {
        //TODO вывести тип счета и баланс
    }

    @Override
    public String toString() {
        return "Saving Account{" +
                "balance=" + balance +
                ", overdraft=" + overdraft +
                '}';
    }
}
