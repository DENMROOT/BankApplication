package com.luxoft.bankapp.service.services;

/**
 * Created by Makarov Denis on 03.02.2015.
 */
public class ServiceFactory {

    public static AccountServiceImpl getAccountServiceImpl() {
        return AccountServiceImpl.getInstance();
    }

    public static BankServiceImpl getBankServiceImpl() {
        return BankServiceImpl.getInstance();
    }

    public static ClientServiceImpl getClientServiceImpl() {
        return ClientServiceImpl.getInstance();
    }
}
