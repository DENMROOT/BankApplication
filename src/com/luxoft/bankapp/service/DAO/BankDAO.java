package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.Bank;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public interface BankDAO {
    /**
     * Finds Bank by its name.
     * Do not load the list of the clients.
     * @param name
     * @return
     */
    Bank getBankByName(String name);
}
