package com.luxoft.bankapp.service.services;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.DAO.ClientDAO;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;

import java.util.*;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class BankServiceImpl implements BankService {

    private static BankServiceImpl instance;
    private BankDAOImpl bankDAO;
    private ClientDAOImpl clientDAO;

    private BankServiceImpl (){};

    public static BankServiceImpl getInstance() {
        if (instance == null) {
            instance = new BankServiceImpl();
        }
        return instance;
    }

    @Override
    public synchronized Set<Client> getClients(Bank bank) {

        Set<Client> clientsList = new HashSet <> (clientDAO.getAllClients(bank));

        return clientsList;
    }

    @Override
    public synchronized Set<Account> getAllAccounts(Bank bank) {
        Set <Account> bankAccounts = new HashSet <Account> ();
        Set<Client> clientsList = new HashSet <> (clientDAO.getAllClients(bank));

        for (Client clientIterator : clientsList) {
            for (Account accountIterator : clientIterator.getAccounts()){
                bankAccounts.add(accountIterator);
            }
        }

        return bankAccounts;
    }

    @Override
    public List<Client> findClientsByCityName(Bank bank, String city, String clientName) {

        List <Client> clientsList = new ArrayList<>(clientDAO.getAllClients(bank));
        List <Client> processedClientsList = new ArrayList<> ();
        for (Client clientIterator: clientsList) {
            if (!clientName.equals("") && (!city.equals(""))) {
                if (clientIterator.getName().equals(clientName) && clientIterator.getCity().equals(city)) {
                    processedClientsList.add(clientIterator);
                }
            } else
                if (clientName.equals("") && (!city.equals(""))) {
                    if (clientIterator.getCity().equals(city)) {
                        processedClientsList.add(clientIterator);
                    }
                } else
                    if (!clientName.equals("") && (city.equals(""))) {
                        if (clientIterator.getName().equals(clientName)) {
                            processedClientsList.add(clientIterator);
                        }
                    } else {
                        return clientsList;
                    }
        }

        return processedClientsList;
    }

    public void setBankDAO(BankDAOImpl bankDAO) {
        this.bankDAO = bankDAO;
    }

    public BankDAOImpl getBankDAO() {
        return bankDAO;
    }

    public void setClientDAO(ClientDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
    }

    public ClientDAOImpl getClientDAO() {
        return clientDAO;
    }
}
