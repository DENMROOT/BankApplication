package com.luxoft.bankapp.model;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class NotEnoughFundsException extends BankException {
    Float amount;

    NotEnoughFundsException(Float amount){
        this.amount=amount;
    }

    public NotEnoughFundsException() {
        this.amount=0.0f;
    }

    @Override
    public String getMessage(){
        return "Попытка списания недостаточных средств";
    }
}
