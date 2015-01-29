package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.ClientDAO;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;
import com.luxoft.bankapp.service.exceptions.DAOException;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;

import java.io.*;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Makarov Denis on 29.01.2015.
 */
public class ClientServiceImpl implements ClientService{
    @Override
    public void addClient(Bank bank, Client client) throws ClientExcistsException {
        ClientDAOImpl clientDao = new ClientDAOImpl();
        try {
            clientDao.insert(client);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Client findClientByName(Bank bank, String clientName) {
        ClientDAO clientDAO = new ClientDAOImpl();
        try {
            Client client = clientDAO.findClientByName(bank, clientName);
            return client;
        } catch (ClientNotFoundException e) {
            e.getMessage();
        }

        return null;
    }

    @Override
    public Set<Account> getClientAccounts(Client client) {
        return client.getAccounts();
    }

    @Override
    public float getClientBalance(Bank bank, Client client) {
        return 0.0f;
    }


    @Override
    public void saveClient(Client client) throws IOException {
        File dir = new File("clients");
        dir.mkdir();
        try (ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("clients/saveClient")))) {
            os.writeObject(client);
            os.flush();
        }
    }

    @Override
    public Client loadClient() throws ClassNotFoundException, IOException {
        Client client = null;
        try (ObjectInputStream oi = new ObjectInputStream(new BufferedInputStream(new FileInputStream("clients/saveClient")))) {
            client = (Client)oi.readObject();
        }
        return client;
    }

    @Override
    public void deleteClient(Bank bank, Client client) {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        clientDAO.remove(client);
    }
}
