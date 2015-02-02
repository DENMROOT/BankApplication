package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.AccountDAOImpl;
import com.luxoft.bankapp.service.DAO.ClientDAO;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;
import com.luxoft.bankapp.service.exceptions.DAOException;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;

import java.io.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Makarov Denis on 29.01.2015.
 */
public class ClientServiceImpl implements ClientService{
    @Override
    public void addClient(Bank bank, Client client) throws ClientExcistsException {
        ClientDAOImpl clientDao = new ClientDAOImpl();
        try {
            clientDao.findClientByName(bank, client.getName());
            throw new ClientExcistsException();
        } catch (ClientNotFoundException e) {
            try {
                clientDao.insert(bank, client);
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } catch (DAOException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

    @Override
    public Client findClientByName(Bank bank, String clientName) {
        ClientDAO clientDAO = new ClientDAOImpl();
        try {
            Client client = clientDAO.findClientByName(bank, clientName);
            return client;
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Account> getClientAccounts(Client client) {
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        Set <Account> accountsList = new HashSet<>(accountDAO.getClientAccounts(client.getClientID()));
        return accountsList;
    }

    @Override
    public float getClientBalance(Bank bank, Client client) {
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        List<Account> accountsList = accountDAO.getClientAccounts(client.getClientID());
        float balance=0.0f;

        for (Account accountsIterator : accountsList) {
            balance+=accountsIterator.getBalance();
        }

        return balance;
    }

    @Override
    public void saveClient(Client client) throws IOException {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        clientDAO.save(client);
    }

    @Override
    public void deleteClient(Bank bank, Client client) {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        clientDAO.remove(client);
    }
}
