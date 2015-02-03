package com.luxoft.bankapp.model.service;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.services.BankServiceImpl;
import com.luxoft.bankapp.service.services.ServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Makarov Denis on 03.02.2015.
 */
public class BankServiceTest {
    Bank testBank = new Bank();
    BankServiceImpl bankService;
    BankDAOImpl bankDao;

    @Before
    public void InitializeBank(){
        bankService = ServiceFactory.getBankServiceImpl();
        bankDao = DaoFactory.getBankDAO();
        String bankName = "My Bank";
        testBank = bankDao.getBankByName(bankName);
        //System.out.println("Банк: " + testBank.getName() +" ID: " +testBank.getBankID());

    }

    @After
    public void FinalizeBank(){
        bankService = null;
        bankDao = null;
        testBank = null;
    }

    @Test
    public void testGetClients (){
        assertEquals("Ошибка при выполнении Тест: testGetClients ",bankService.getClients(testBank).size(), 6, 0);
    }

    @Test
    public void testGetAccounts (){
        assertEquals("Ошибка при выполнении Тест: testGetAccounts ",bankService.getAllAccounts(testBank).size(), 8, 0);
    }
}
