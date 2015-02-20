package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Makarov Denis on 29.01.2015.
 */
public interface ClientService {
    public void addClient(Bank bank,Client client) throws ClientExcistsException;
    public Client findClientByName(Bank bank, String clientName);
    public Client findClientById(Bank bank, long clientId);
    public Set<Account> getClientAccounts(Client client);
    public float getClientBalance(Bank bank, Client client);
    public void saveClient(Client client) throws IOException;
    public void deleteClient (Bank bank, Client client);
}
