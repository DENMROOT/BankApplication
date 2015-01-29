package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.exceptions.DAOException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public interface ClientDAO {
/**
 * Return client by its name, initialize client accounts.
 * @param bank
 * @param name
 * @return
 */
        Client findClientByName(Bank bank, String name) throws ClientNotFoundException;

        /**
         * Return client by its name, initialize client accounts.
         * @param bank
         * @param name
         * @return
         */
        Client findClientById(int clientId) throws ClientNotFoundException;

        /**
         * Returns the list of all clients of the Bank
         * and their accounts
         * @param bankId
         * @return
         */
        List<Client> getAllClients(Bank bank);

        /**
         * Method should sace Client (if id==null)
         * or update client in database (if id!=null)
         * @param client
         */

        void save(Client client);

        /**
         * Method should insert new Client (if id==null)
         * or update client in database (if id!=null)
         * @param client
         */
        void insert(Client client) throws SQLException, DAOException;

        /**
         * Method removes client from Database
         * @param client
         */
        void remove(Client client);
        }
