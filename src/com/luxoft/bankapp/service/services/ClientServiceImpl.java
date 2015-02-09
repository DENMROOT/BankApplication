package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.AccountDAOImpl;
import com.luxoft.bankapp.service.DAO.ClientDAO;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
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

/**
 * Created by Makarov Denis on 29.01.2015.
 */
public class ClientServiceImpl implements ClientService{

    private static ClientServiceImpl instance;
    Lock clientLock = new ReentrantLock();

    private ClientServiceImpl(){};

    public static ClientServiceImpl getInstance (){
        if (instance == null) {
            instance = new ClientServiceImpl();
        }
        return instance;
    }

    @Override
    public void addClient(Bank bank, Client client) throws ClientExcistsException {
        ClientDAOImpl clientDao = DaoFactory.getClientDAO();
        try {
            clientDao.findClientByName(bank, client.getName());
            throw new ClientExcistsException("Клиент с указанным именем уже существует");
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
        //clientLock.lock();
        //try {
            ClientDAO clientDAO = DaoFactory.getClientDAO();
            try {
                Client client = clientDAO.findClientByName(bank, clientName);
                return client;
            } catch (ClientNotFoundException e) {
                System.out.println(e.getMessage());
                return null;
            }
        //} finally {
        //    clientLock.unlock();
        //}
    }

    @Override
    public Set<Account> getClientAccounts(Client client) {
        //clientLock.lock();
        //try {
            AccountDAOImpl accountDAO = DaoFactory.getAccountDAO();
            Set <Account> accountsList = new HashSet<>(accountDAO.getClientAccounts(client.getClientID()));
            return accountsList;
        //} finally {
        //    clientLock.unlock();
        //}
    }

    @Override
    public float getClientBalance(Bank bank, Client client) {
        //clientLock.lock();
        //try {
            Set<Account> accountsList = getClientAccounts(client);
            float balance=0.0f;

            for (Account accountsIterator : accountsList) {
                balance+=accountsIterator.getBalance();
            }

            return balance;
        //} finally {
        //    clientLock.unlock();
        //}

    }

    @Override
    public void saveClient(Client client) throws IOException {
        ClientDAOImpl clientDAO = DaoFactory.getClientDAO();
        clientDAO.save(client);
    }

    @Override
    public void deleteClient(Bank bank, Client client) {
        ClientDAOImpl clientDAO = DaoFactory.getClientDAO();
        clientDAO.remove(client);
    }
}
