package com.luxoft.bankapp.service.DAO;

/**
 * Created by Makarov Denis on 03.02.2015.
 */
public class DaoFactory {
    public static BankDAOImpl getBankDAO() {
        return BankDAOImpl.getInstance();
    }

    public static ClientDAOImpl getClientDAO() {
        return ClientDAOImpl.getInstance();
    }

    public static AccountDAOImpl getAccountDAO() {
        return AccountDAOImpl.getInstance();
    }
}
