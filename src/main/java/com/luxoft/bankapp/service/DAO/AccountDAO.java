package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

import java.util.List;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public interface AccountDAO {
    /**
     * Save Account in database
     * @param account
     */
    public void save(Account account);

    /**
     * Insert Account in database
     * @param account
     */
    public void insert(Client client, Account account);

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

    /**
     * Get account of the client
     * @param id Id of the client
     * @param accountID Id of the account
     */
    public float getClientAccountBalance(long accountID);

    /**
     * Get all accounts of the client
     * @param accountFrom Id of account FROM transaction
     * @param accountTo Id of account TO transaction
     * @param amount Id amount of funds to transfer
     */
    public void transferFunds(Account accountFrom, Account accountTo, float amount);
}
