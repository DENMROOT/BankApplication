package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;

/**
 * Created by Makarov Denis on 29.01.2015.
 */
public interface AccountService {
    public void addAccount(Client client, Account account);
    public void setActiveAccount(Client client, Account account);
    public void withdrawFromAccount(Client client, Account account, float withdrawalSum) throws NotEnoughFundsException;
    public void transferFunds(Account accountFrom, Account accountTo, float amount) throws NotEnoughFundsException;
    public void depositToAccount(Client client, Account account, float depositSum);
    public Account findAccountByID(Client client, Long accountID);
}
