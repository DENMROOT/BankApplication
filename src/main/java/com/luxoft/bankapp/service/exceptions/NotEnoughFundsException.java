package com.luxoft.bankapp.service.exceptions;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class NotEnoughFundsException extends BankException {
    Float amount;

    public NotEnoughFundsException(Float amount){
        this.amount=amount;
    }

    public NotEnoughFundsException() {
        super();
    }

    public NotEnoughFundsException(String string) {
        super(string);
    }
}
