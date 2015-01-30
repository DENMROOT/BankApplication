package com.luxoft.bankapp.service.services;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class BankServiceImpl implements BankService {

    @Override
    public Set<Client> getClients(Bank bank) {
        ClientDAOImpl clientDAO = new ClientDAOImpl();

        Set<Client> clientsList = new HashSet <> (clientDAO.getAllClients(bank));

        return clientsList;
    }

    @Override
    public Set<Account> getAllAccounts(Bank bank) {
        Set <Account> bankAccounts = new HashSet <Account> ();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        Set<Client> clientsList = new HashSet <> (clientDAO.getAllClients(bank));

        for (Client clientIterator : clientsList) {
            for (Account accountIterator : clientIterator.getAccounts()){
                bankAccounts.add(accountIterator);
            }
        }

        return bankAccounts;
    }

}
