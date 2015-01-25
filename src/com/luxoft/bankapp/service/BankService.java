package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public interface BankService {
    public void addClient(Bank bank,Client client) throws ClientExcistsException;
    public void removeClient(Bank bank,Client client);
    public void addAccount(Client client, Account account);
    public void setActiveAccount(Client client, Account account);
    public void withdrawFromAccount(Client client, Account account, float withdrawalSum) throws NotEnoughFundsException;
    public void depositToAccount(Client client, Account account, float depositSum);
    public Client findClient(Bank bank, String clientName);
    public Set<Client> getClients(Bank bank);
    public Set<Account> getClientAccounts(Client client);
    public Set<Account> getAllAccounts (Bank bank);
    public Client getClientByName(Bank bank, String clientName);
    public void saveClient(Client client) throws IOException;
    public Client loadClient() throws ClassNotFoundException, IOException;
    public Account findAccountByID(Client client, Long accountID);
    public void deleteClient (Bank bank, Client client);
}
