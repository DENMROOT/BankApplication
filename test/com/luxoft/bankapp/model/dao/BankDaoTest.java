package com.luxoft.bankapp.model.dao;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.ClientRegistrationListener;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.services.BankServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BankDaoTest {

    Bank testBank;

    @Before
    public void InitializeBank(){
        BankDAOImpl bankDao = DaoFactory.getBankDAO();
        String bankName = "My Bank";
        testBank = bankDao.getBankByName(bankName);
        //System.out.println("Банк: " + testBank.getName() +" ID: " +testBank.getBankID());

    }

    @After
    public void FinalizeBank(){
        List<ClientRegistrationListener> listeners = null;
        //listeners.add(new Bank.PrintClientListener());
        //listeners.add(new Bank.EmailNotificationListener());
        testBank = null;

    }

    @Test
    public void testGetName() throws Exception {
        assertTrue("Ошибка при выполнении Тест: testSetName - имя банка не совпадает", testBank.getName().equals("My Bank"));
    }

    @Test
    public void getClients() throws Exception {
        BankServiceImpl myBankService = BankServiceImpl.getInstance();
        assertEquals("Ошибка при выполнении Тест: getClients ",myBankService.getClients(testBank).size(), 6, 0);
    }

    @Test
    public void getAllAccounts() throws Exception {
        BankServiceImpl myBankService = BankServiceImpl.getInstance();
        assertEquals("Ошибка при выполнении Тест: getAllAccounts ",myBankService.getAllAccounts(testBank).size(), 8, 0);
    }

    @Test
    public void testPrintReport() throws Exception {

    }


}