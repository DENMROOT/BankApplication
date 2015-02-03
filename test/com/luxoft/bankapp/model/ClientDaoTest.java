package com.luxoft.bankapp.model;

import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.DAO.AccountDAOImpl;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.exceptions.DAOException;
import com.luxoft.bankapp.service.services.ClientServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import service.TestService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ClientDaoTest {
    private Bank testBank;
    private Client client1;
    private Client client2;
    private Client client3;
    private BankDAOImpl myBankDao;
    private ClientDAOImpl myClientDao;
    private AccountDAOImpl myAccountDao;

    @Before
    public void InitializeClient(){
        myBankDao = DaoFactory.getBankDAO();
        myClientDao = DaoFactory.getClientDAO();
        myAccountDao = DaoFactory.getAccountDAO();
        testBank = myBankDao.getBankByName("My Bank");
        System.out.println(testBank.getBankID() + " " + testBank.getName());
        client1 = new Client(Gender.MALE);
        client1.setName("Jim Kerry");
        client1.setEmail("jim@gmail.com");
        client1.setPhone("(098)3332211");
        client1.setCity("Львов");
        client3 = new Client(Gender.MALE);
        client3.setName("Arnold Bush");
        client3.setEmail("arnold@gmail.com");
        client3.setPhone("(087)3332211");
        client3.setCity("Львов");
    }

    @After
    public void FinalizeClient(){
        myClientDao.remove(client1);
        myClientDao.remove(client3);
        client1 = null;
        client2 = null;
        client3 = null;
        myBankDao = null;
        myClientDao = null;
    }


    @Test
    public void ClientInsertRead () throws ClientNotFoundException, SQLException, DAOException {
        //myClientDao.remove(myClientDao.findClientByName(testBank, "Arnold Bush"));
        myClientDao.insert(testBank, client3);

        client2 = myClientDao.findClientByName(testBank, "Arnold Bush");
        assertTrue(TestService.isEquals(client3, client2));
    }

    @Test
    public void ClientUpdate () throws ClientNotFoundException, SQLException, DAOException {
        //myClientDao.remove(myClientDao.findClientByName(testBank, "Jim Kerry"));
        myClientDao.insert(testBank, client1);
        SavingAccount clientAccount = new SavingAccount(1,500.0f);
        myAccountDao.insert(myClientDao.findClientByName(testBank, "Jim Kerry"), clientAccount);
        client1.addAccount(clientAccount);
        client1.setName("Jim Kerry");
        client1.setEmail("jimmy@ukr.net");
        client1.setPhone("(067)1112233");
        client1.setCity("Запорожье");

        //System.out.println(myAccountDao.getClientAccounts(myClientDao.findClientByName(testBank, "Jim Kerry").getClientID()));
        client1.setActiveAccount(clientAccount);

        myClientDao.save(client1);
        client2 = myClientDao.findClientByName(testBank, "Jim Kerry");
        assertTrue(TestService.isEquals(client1, client2));
    }
}