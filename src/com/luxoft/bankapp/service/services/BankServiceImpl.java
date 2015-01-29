package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class BankServiceImpl implements BankService {

    @Override
    public Set<Client> getClients(Bank bank) {
        return bank.getClients();
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

}
