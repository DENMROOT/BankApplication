package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.*;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class BankServiceImpl implements BankService {
    @Override
    public void addClient(Bank bank, Client client) throws ClientExcistsException {
        bank.addClient(client);
    }

    @Override
    public void removeClient(Bank bank, Client client) {
        bank.removeClient(client);
    }

    @Override
    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    @Override
    public void withdrawFromAccount(Client client, Account account, float withdrawalSum) throws NotEnoughFundsException, OverDraftLimitExceededException {
        client.withdrawFromAccount(account, withdrawalSum);
    }

    @Override
    public void depositToAccount(Client client, Account account, float depositSum) {
        client.depositToAccount(account, depositSum);
    }

    public Account findAccountByID(Client client, Long accountID) {
        return client.getAccountById(accountID);
    }

    @Override
    public void deleteClient(Bank bank, Client client) {
        bank.deleteClient(client);
    }

    @Override
    public Client findClient(Bank bank, String clientName) {
        Client findedClient = new Client();
        findedClient=bank.findClient(clientName);
        return findedClient;
    }

    @Override
    public Set<Client> getClients(Bank bank) {
        return bank.getClients();
    }

    @Override
    public Set<Account> getClientAccounts(Client client) {
        return client.getAccounts();
    }

    @Override
    public Set<Account> getAllAccounts(Bank bank) {
        Set <Client> bankClients = new HashSet<Client>(bank.getClients());
        Set <Account> bankAccounts = new HashSet <Account> ();

        for (Iterator<Client> it = bankClients.iterator(); it.hasNext(); ) {
            Client f = it.next();
            Set <Account> clientAccounts = new HashSet <Account> (f.getAccounts());
            for (Iterator<Account> accIterator = clientAccounts.iterator(); accIterator.hasNext(); ) {
                Account a = accIterator.next();
                bankAccounts.add(a);
            }
        }
        return bankAccounts;
    }

    @Override
    public Client getClientByName(Bank bank, String clientName) {
        return bank.getClient(clientName);
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

    public float getClientBalance(Bank bank, Client client) {
        return client.getClientBalance(client);
    }
}
