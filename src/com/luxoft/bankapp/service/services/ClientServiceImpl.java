package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.*;
import com.luxoft.bankapp.service.exceptions.DAOException;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;

import java.io.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 29.01.2015.
 */
public class ClientServiceImpl implements ClientService{

    private static ClientServiceImpl instance;
    Logger clientServiceLog = Logger.getLogger("ClientServiceImpl");

    private ClientServiceImpl(){};

    public static ClientServiceImpl getInstance (){
        if (instance == null) {
            instance = new ClientServiceImpl();
        }
        return instance;
    }

    @Override
    public synchronized void addClient(Bank bank, Client client) throws ClientExcistsException {
        ClientDAO clientDao = DaoFactory.getClientDAO();
        try {
            clientDao.findClientByName(bank, client.getName());
            throw new ClientExcistsException("Клиент с указанным именем уже существует");
        } catch (ClientNotFoundException e) {
            try {
                clientDao.insert(bank, client);
            } catch (SQLException | DAOException e1 ) {
                System.out.println(e1.getMessage());
                clientServiceLog.severe("Exception: " + e1.getMessage());
            }
        }
    }

    @Override
    public synchronized Client findClientByName(Bank bank, String clientName) {
        ClientDAO clientDAO = DaoFactory.getClientDAO();
        try {
            Client client = clientDAO.findClientByName(bank, clientName);
            return client;
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            clientServiceLog.severe("Exception: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Client findClientById(Bank bank, long clientId) {
        ClientDAO clientDAO = DaoFactory.getClientDAO();
        try {
            Client client = clientDAO.findClientById(bank, clientId);
            return client;
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            clientServiceLog.severe("Exception: " + e.getMessage());
            return null;
        }
    }

    @Override
    public synchronized Set<Account> getClientAccounts(Client client) {
        AccountDAO accountDAO = DaoFactory.getAccountDAO();
        Set <Account> accountsList = new HashSet<>(accountDAO.getClientAccounts(client.getClientID()));
        return accountsList;
    }

    @Override
    public synchronized float getClientBalance(Bank bank, Client client) {
        Set<Account> accountsList = getClientAccounts(client);
        float balance=0.0f;

        for (Account accountsIterator : accountsList) {
            balance+=accountsIterator.getBalance();
        }

        return balance;

    }

    @Override
    public synchronized void saveClient(Client client) throws IOException {
        ClientDAO clientDAO = DaoFactory.getClientDAO();
        clientDAO.save(client);
    }

    @Override
    public synchronized void deleteClient(Bank bank, Client client) {
        ClientDAO clientDAO = DaoFactory.getClientDAO();
        clientDAO.remove(client);
    }
}
