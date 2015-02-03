package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.AccountDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;

import java.util.List;

/**
 * Created by Makarov Denis on 29.01.2015.
 */
public class AccountServiceImpl implements AccountService{

    private static AccountServiceImpl instance;

    private AccountServiceImpl (){};

    public static AccountServiceImpl getInstance() {
        if (instance == null) {
            instance = new AccountServiceImpl();
        }
        return instance;
    }

    @Override
    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    @Override
    public void withdrawFromAccount(Client client, Account account, float withdrawalSum) throws NotEnoughFundsException {
        client.withdrawFromAccount(account, withdrawalSum);
        AccountDAOImpl accountDAO = DaoFactory.getAccountDAO();
        accountDAO.save(account);
    }

    @Override
    public void transferFunds(Account accountFrom, Account accountTo, float amount) throws NotEnoughFundsException {
        accountFrom.withdraw(amount);
        accountTo.deposit(amount);
        AccountDAOImpl accountDAO = DaoFactory.getAccountDAO();
        accountDAO.transferFunds(accountFrom, accountTo , amount);
    }

    @Override
    public void depositToAccount(Client client, Account account, float depositSum) {
        client.depositToAccount(account, depositSum);
        AccountDAOImpl accountDAO = DaoFactory.getAccountDAO();
        accountDAO.save(account);
    }

    @Override
    public Account findAccountByID(Client client, Long accountID) {
        AccountDAOImpl accountDAO = DaoFactory.getAccountDAO();
        List<Account> accountsList = accountDAO.getClientAccounts(client.getClientID());

        for (Account accountIterator : accountsList) {
            if (accountIterator.getAccountId() == accountID) {
                return accountIterator;
            }
        }

        return null;
    }
}
