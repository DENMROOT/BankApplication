package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.AccountDAO;
import com.luxoft.bankapp.service.DAO.AccountDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    public synchronized void  addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    @Override
    public synchronized void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    @Override
    public synchronized void withdrawFromAccount(Client client, Account account, float withdrawalSum) throws NotEnoughFundsException {
        AccountDAO accountDAO = DaoFactory.getAccountDAO();
        account.setBalance(accountDAO.getClientAccountBalance(account.getAccountId()));
        client.withdrawFromAccount(account, withdrawalSum);
        accountDAO.save(account);

    }

    @Override
    public synchronized void transferFunds(Account accountFrom, Account accountTo, float amount) throws NotEnoughFundsException {
        accountFrom.withdraw(amount);
        accountTo.deposit(amount);
        AccountDAO accountDAO = DaoFactory.getAccountDAO();
        accountDAO.transferFunds(accountFrom, accountTo , amount);

    }

    @Override
    public synchronized void depositToAccount(Client client, Account account, float depositSum) {
        client.depositToAccount(account, depositSum);
        AccountDAO accountDAO = DaoFactory.getAccountDAO();
        accountDAO.save(account);
    }

    @Override
    public synchronized Account findAccountByID(Client client, Long accountID) {
        AccountDAO accountDAO = DaoFactory.getAccountDAO();
        List<Account> accountsList = accountDAO.getClientAccounts(client.getClientID());

        for (Account accountIterator : accountsList) {
            if (accountIterator.getAccountId() == accountID) {
                return accountIterator;
            }
        }

        return null;
    }
}
