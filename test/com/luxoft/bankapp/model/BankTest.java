package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.ClientRegistrationListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

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
    public void testAddClient() throws Exception {
        TestingClient assertClient = new TestingClient(Gender.MALE);
        assertClient.setName("Testing Client");

        testBank.addClient(testClient);
        assertEquals("Ошибка при выполнении Тест: testAddClient - типы объектов клиент не совпадают",testBank.getClient("Testing Client"),assertClient);
        assertTrue("Ошибка при выполнении Тест: testAddClient - имя клиента не совпадает", testBank.getClient("Testing Client").getName().equals(assertClient.getName()));
    }

    @Test
    public void testGetClient() throws Exception {
        TestingClient assertClient = new TestingClient(Gender.MALE);
        assertClient.setName("Testing Client");

        testBank.addClient(testClient);
        assertEquals("Ошибка при выполнении Тест: testGetClient - типы объектов клиент не совпадают",testBank.getClient("Testing Client"),assertClient);
        assertTrue("Ошибка при выполнении Тест: testGetClient - имя клиента не совпадает", testBank.getClient("Testing Client").getName().equals(assertClient.getName()));
    }

    @Test
    public void testPrintReport() throws Exception {

    }

    @Test
    public void testGetClients() throws Exception {
        TestingClient assertClient = new TestingClient(Gender.MALE);
        assertClient.setName("Testing Client");

        testBank.addClient(testClient);

        Set<Client> clients = new HashSet<>();
        clients = testBank.getClients();
        boolean containsClient = clients.contains(assertClient);

        assertTrue("Ошибка при выполнении Тест: testGetClients - Указанный клиент в списке банка отсутствует", containsClient);
    }

    @Test
    public void testDeleteClient() throws Exception {
        TestingClient assertClient = new TestingClient(Gender.MALE);
        assertClient.setName("Testing Client");

        testBank.addClient(testClient);
        testBank.deleteClient(testClient);

        Set<Client> clients = new HashSet<>();
        clients = testBank.getClients();
        boolean containsClient = clients.contains(assertClient);

        assertTrue("Ошибка при выполнении Тест: testDeleteClient - Удаление клиента работает неправильно", !containsClient);
    }

    @Test
    public void testContainsClientAllready() throws Exception {
        TestingClient assertClient = new TestingClient(Gender.MALE);
        assertClient.setName("Testing Client");

        testBank.addClient(testClient);
        boolean containsClient = testBank.containsClientAllready(assertClient);

        assertTrue("Ошибка при выполнении Тест: testContainsClientAllready - Указанный клиент в списке банка отсутствует", containsClient);
    }

    @Test
    public void testFindClient() throws Exception {

        testBank.addClient(testClient);
        boolean equalsClient = testClient.getName().equals(testBank.getClient("Testing Client").getName());

        assertTrue("Ошибка при выполнении Тест: testFindClient - Указанный клиент в списке банка не найден", equalsClient);
    }

    @Test
    public void testParseFeed() throws Exception {

    }
}