package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.Account;

import java.util.List;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public interface AccountDAO {
    /**
     * Save Account in database
     * @param client
     */
    public void save(Account client);

    /**
     * Remove all accounts of client
     * @param id Id of the client
     */
    public void removeByClientId(long id);

    /**
     * Get all accounts of the client
     * @param id Id of the client
     */
    public List<Account> getClientAccounts(long id);
}
