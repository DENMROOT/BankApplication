package com.luxoft.bankapp.service.services;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public interface BankService {
    public Set<Client> getClients(Bank bank);
    public Set<Account> getAllAccounts (Bank bank);

    List<Client> findClientsByCityName(Bank bank, String city, String clientName);
}
