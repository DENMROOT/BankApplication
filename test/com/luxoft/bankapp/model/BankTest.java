package com.luxoft.bankapp.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@Ignore
public class BankTest {

    private TestingBank testBank;
    private TestingClient testClient;

    private class TestingBank extends Bank {

        public TestingBank(List<ClientRegistrationListener> listeners) {
            super(listeners);
        }
    }


    private class TestingClient extends Client {

        public TestingClient (Gender gender){
            super(gender);
        }
    }

    @Before
    public void InitializeBank(){
        List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
        //listeners.add(new Bank.PrintClientListener());
        //listeners.add(new Bank.EmailNotificationListener());
        testBank = new TestingBank(listeners);
        testBank.setName("Мой первый тестовый банк");
        testClient = new TestingClient(Gender.MALE);
        testClient.setName("Testing Client");

    }

    @After
    public void FinalizeBank(){
        List<ClientRegistrationListener> listeners = null;
        //listeners.add(new Bank.PrintClientListener());
        //listeners.add(new Bank.EmailNotificationListener());
        testBank = null;
        testClient = null;

    }

    @Test
    public void testSetName() throws Exception {
        assertEquals("Ошибка при выполнении Тест: testSetName",testBank.getName(),"Мой первый тестовый банк");
        assertTrue("Ошибка при выполнении Тест: testSetName - имя банка не совпадает",testBank.getName().equals("Мой первый тестовый банк"));
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Ошибка при выполнении Тест: testGetName",testBank.getName(),"Мой первый тестовый банк");
        assertTrue("Ошибка при выполнении Тест: testGetName - имя банка не совпадает",testBank.getName().equals("Мой первый тестовый банк"));
    }


    @Test
    public void testPrintReport() throws Exception {

    }


}