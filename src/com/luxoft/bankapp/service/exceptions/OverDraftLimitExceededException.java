package com.luxoft.bankapp.service.exceptions;

import com.luxoft.bankapp.model.Account;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class OverDraftLimitExceededException extends NotEnoughFundsException {
    private Account exceptionAccount;
    private Float availableMoney=0.0f;

    public OverDraftLimitExceededException(Account account,Float availableMoney){
        this.exceptionAccount=account;
        this.availableMoney=availableMoney;

    }

    public Float getAvailableMoney() {
        return availableMoney;
    }

    public Account getExceptionAccount() {
        return exceptionAccount;
    }

    @Override
    public String getMessage(){
        return "Сумма списания превышает доступный овердрафт";
    }

}