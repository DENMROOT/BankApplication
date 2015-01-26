package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.ClientRegistrationListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ClientTest {
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

    private class TestingSavingAccount extends SavingAccount  {

        public TestingSavingAccount(long accountId, float x) {
            super(accountId, x);
        }
    }
    private class TestingCheckingAccount extends CheckingAccount  {

        public TestingCheckingAccount(long accountId, float balance, float overdraft) {
            super(accountId, balance, overdraft);
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
        TestingSavingAccount savingAccount = new TestingSavingAccount((long) 1 ,450.0f);
        TestingCheckingAccount checkingAccount = new TestingCheckingAccount((long) 2,450.0f, 1000.0f);
        testClient.addAccount(savingAccount);
        testClient.addAccount(checkingAccount);

    }

    @After
    public void FinalizeBank(){
        List<ClientRegistrationListener> listeners = null;
        //listeners.add(new Bank.PrintClientListener());
        //listeners.add(new Bank.EmailNotificationListener());
        testBank = null;
        testClient = null;
        TestingSavingAccount savingAccount = null;
        TestingCheckingAccount checkingAccount = null;

    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testPrintReport() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {
        boolean assertBoolean = testClient.getName().equals("Testing Client");
        assertTrue("Ошибка при выполнении Тест:  testGetName - имя клиента не совпадает", assertBoolean);

    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetName() throws Exception {
        testClient.setName("Test Makarov");
        boolean assertBoolean = testClient.getName().equals("Test Makarov");

        assertTrue("Ошибка при выполнении Тест:  testSetName - имя клиента не совпадает",assertBoolean);

        /*
        Установка некорректного Name, должны получить IllegalArgumentException
         */
        testClient.setName("Test123 Makarov123");
    }

    @Test
    public void testAddAccount() throws Exception {

        Set <Account> assertAccounts = testClient.getAccounts();
        SavingAccount assertSavingAccount = new SavingAccount((long) 1 ,450.0f);
        CheckingAccount assertCheckingAccount = new CheckingAccount((long) 2 ,450.0f, 1000.0f);

        boolean idBoolean1 = assertAccounts.contains(assertSavingAccount);
        boolean idBoolean2 = assertAccounts.contains(assertCheckingAccount);
        assertTrue("Ошибка при выполнении Тест:  testAddAccount - id добавленного счета не совпадает",idBoolean1);
        assertTrue("Ошибка при выполнении Тест:  testAddAccount - id добавленного счета не совпадает",idBoolean2);
    }

    @Test
    public void testSetActiveAccount() throws Exception {
        Account assertActiveAccount = testClient.getActiveAccount();

        boolean strEquals = assertActiveAccount.toString().equals("Checking Account{Account ID=2,balance=450.0, overdraft=1000.0}");
        assertTrue("Ошибка при выполнении Тест:  testSetActiveAccount - Ошибка получения активного счета",strEquals);

    }

    @Test
    public void testGetClientSalutation() throws Exception {
        boolean strEquals = testClient.getClientSalutation().toString().equals("Mr.");
        assertTrue("Ошибка при выполнении Тест:  testGetClientSalutation - Ошибка получения обращения к клиенту",strEquals);
    }

    @Test
    public void testWithdrawFromAccount() throws Exception {
        Account assertActiveAccount = testClient.getActiveAccount();
        testClient.withdrawFromAccount(assertActiveAccount,300.0f);

        /*
        списываем с текущего счета 300 , должны получить баланс 450 - 300 = 150 в отчете по счету.
         */
        boolean strEquals = assertActiveAccount.toString().equals("Checking Account{Account ID=2,balance=150.0, overdraft=1000.0}");
        assertTrue("Ошибка при выполнении Тест:  testWithdrawFromAccount - Ошибка списания со счета",strEquals);

    }

    @Test
    public void testDepositToAccount() throws Exception {
        Account assertActiveAccount = testClient.getActiveAccount();
        testClient.depositToAccount(assertActiveAccount, 300.0f);

        /*
        списываем с текущего счета 300 , должны получить баланс 450 + 300 = 750 в отчете по счету.
         */
        boolean strEquals = assertActiveAccount.toString().equals("Checking Account{Account ID=2,balance=750.0, overdraft=1000.0}");
        assertTrue("Ошибка при выполнении Тест:  testDepositToAccount - Ошибка пополнения счета",strEquals);

    }

    @Test
    public void testParseFeed() throws Exception {

    }

    @Test  (expected = IllegalArgumentException.class)
    public void testSetEmail() throws Exception {
        testClient.setEmail("denm2000@ukr.net");
        assertTrue("Ошибка при выполнении Тест:  testSetEmail - Ошибка присваивания email",testClient.getEmail().toString().equals("denm2000@ukr.net"));

        /*
        Установка некорректного email, должны получить IllegalArgumentException
         */
        testClient.setEmail("denm2000ukrnet@123");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone() throws Exception {
        testClient.setPhone("(067)1234455");
        assertTrue("Ошибка при выполнении Тест:  testSetPhone - Ошибка присваивания phone",testClient.getPhone().toString().equals("(067)1234455"));

        /*
        Установка некорректного phone, должны получить IllegalArgumentException
         */
        testClient.setPhone("067-1234455");
    }

    @Test
    public void testGetAccountById() throws Exception {
        assertTrue("",testClient.getAccountById((long) 1).toString().equals("Saving Account{Account ID=1,balance=450.0, overdraft=0.0}"));
    }

}