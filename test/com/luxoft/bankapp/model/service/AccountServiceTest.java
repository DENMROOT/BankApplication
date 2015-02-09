package com.luxoft.bankapp.model.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.BankDAO;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.services.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Makarov Denis on 03.02.2015.
 */
public class AccountServiceTest {
    Bank testBank = new Bank();
    BankServiceImpl bankService;
    AccountService accountService;
    ClientService clientService;
    BankDAO bankDao;

    @Before
    public void InitializeBank(){
        bankService = ServiceFactory.getBankServiceImpl();
        accountService = ServiceFactory.getAccountServiceImpl();
        clientService = ServiceFactory.getClientServiceImpl();
        bankDao = DaoFactory.getBankDAO();
        String bankName = "My Bank";
        testBank = bankDao.getBankByName(bankName);

    }

    @After
    public void FinalizeBank(){
        bankService = null;
        clientService = null;
        accountService = null;
        bankDao = null;
        testBank = null;
    }

    @Test
    public void testFindAccountByID (){
        String str = accountService.findAccountByID(clientService.findClientByName(testBank, "Денис Макаров"), Long.valueOf(1)).toString();
        assertEquals(str, "Saving Account{Account ID=1,balance=500.0, overdraft=0.0}");
    }

    @Test
    public void testDepositToAccountWithdrowFromAccount () throws NotEnoughFundsException {
        float amount = 150.0f;
        Client client = clientService.findClientByName(testBank,"Денис Макаров");
        Account account = accountService.findAccountByID(clientService.findClientByName(testBank, "Денис Макаров"), Long.valueOf(1));
        accountService.depositToAccount(client, account,amount);
        account = accountService.findAccountByID(clientService.findClientByName(testBank, "Денис Макаров"), Long.valueOf(1));
        assertEquals(account.getBalance(),650.0f, 0.0f);
        accountService.withdrawFromAccount(client, account,amount);
        account = accountService.findAccountByID(clientService.findClientByName(testBank, "Денис Макаров"), Long.valueOf(1));
        assertEquals(account.getBalance(),500.0f, 0.0f);
    }

    @Test (expected = NotEnoughFundsException.class)
    public void testWithdrowFromAccountException () throws NotEnoughFundsException {
        float amount = 550.0f;
        Client client = clientService.findClientByName(testBank,"Денис Макаров");
        Account account = accountService.findAccountByID(clientService.findClientByName(testBank, "Денис Макаров"), Long.valueOf(1));
        accountService.withdrawFromAccount(client, account, amount);
        account = accountService.findAccountByID(clientService.findClientByName(testBank, "Денис Макаров"), Long.valueOf(1));
        assertEquals(account.getBalance(),500.0f, 0.0f);
    }

    @Test
    public void testTransferFunds () throws NotEnoughFundsException {
        float amount = 500.0f;
        Client clientFrom = clientService.findClientByName(testBank,"Денис Макаров");
        Client clientTo = clientService.findClientByName(testBank,"Юра Иванов");
        Account accountFrom = accountService.findAccountByID(clientFrom, Long.valueOf(1));
        Account accountTo = accountService.findAccountByID(clientTo, Long.valueOf(5));
        accountService.transferFunds(accountFrom, accountTo,amount);
        accountFrom = accountService.findAccountByID(clientFrom, Long.valueOf(1));
        accountTo= accountService.findAccountByID(clientTo, Long.valueOf(5));
        assertEquals(accountFrom.getBalance(),0.0f, 0.0f);
        assertEquals(accountTo.getBalance(),800.0f, 0.0f);
        accountService.transferFunds(accountTo, accountFrom,amount);
    }

}
