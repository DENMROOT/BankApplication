package com.luxoft.bankapp.model.service;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.services.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Makarov Denis on 03.02.2015.
 */
public class ClientServiceTest {
    Bank testBank = new Bank();
    BankService bankService;
    ClientService clientService;
    AccountService accountService;
    BankDAOImpl bankDao;

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
    public void testFindClientByName (){
        Client client = clientService.findClientByName(testBank, "Денис Макаров");
        assertEquals(client.getName(), "Денис Макаров");
        assertEquals(client.getGender(), Gender.MALE);
        assertEquals(client.getEmail(), "denm2000@ukr.net");
    }

    @Test
    public void testClientEquals (){
        Client client1 = new Client(Gender.MALE);
        Client client2 = new Client(Gender.MALE);

        client2.setName("Зоя Космодемьянская");
        assertFalse("Ошибка сравнения клиентов", client1.equals(client2));
        client1.setName("Зоя Космодемьянская");
        assertTrue("Ошибка сравнения клиентов", client1.equals(client2));
    }

    @Test
    public void testAddClient () throws ClientExcistsException {
        Client client = new Client(Gender.MALE);
        client.setName("Юрий Спосокукоцкий");
        client.setEmail("spasik@ukr.net");
        client.setPhone("(098)3332211");
        client.setCity("Москва");
        clientService.addClient(testBank,client);
        client = clientService.findClientByName(testBank, "Юрий Спосокукоцкий");
        assertEquals(client.getName(), "Юрий Спосокукоцкий");
        assertEquals(client.getGender(), Gender.MALE);
        assertEquals(client.getEmail(), "spasik@ukr.net");
        clientService.deleteClient(testBank, client);
    }

    @Test (expected = ClientExcistsException.class)
    public void testAddClientException () throws ClientExcistsException, ClientNotFoundException {
        Client client = clientService.findClientByName(testBank, "Денис Макаров");
        clientService.addClient(testBank, client);
        clientService.deleteClient(testBank, client);
    }

    @Test
    public void testGetClientAccounts (){
        Client client = clientService.findClientByName(testBank, "Денис Макаров");
        assertEquals(clientService.getClientAccounts(client).size(),2,0);
    }

    @Test
    public void testGetClientBalance (){
        Client client = clientService.findClientByName(testBank, "Денис Макаров");
        assertEquals(clientService.getClientBalance(testBank,client),400.0,0);
    }

    @Test
    public void testSaveClient () throws IOException {
        Client client = clientService.findClientByName(testBank, "Денис Макаров");
        client.setName("Евгений Мунтян");
        client.setEmail("evgen@ukr.net");
        client.setPhone("(098)3332211");
        client.setCity("Мурманск");
        clientService.saveClient(client);

        client = clientService.findClientByName(testBank, "Евгений Мунтян");
        assertEquals(client.getName(), "Евгений Мунтян");
        assertEquals(client.getEmail(), "evgen@ukr.net");
        assertEquals(client.getPhone(), "(098)3332211");
        assertEquals(client.getCity(), "Мурманск");

        client.setName("Денис Макаров");
        client.setEmail("denm2000@ukr.net");
        client.setPhone("(098)9998811");
        client.setCity("Запорожье");
        clientService.saveClient(client);
    }

}

