package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.exceptions.OverDraftLimitExceededException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Ignore
public class CheckingAccountTest {

    private Bank bank;
    private Client client;

    @Before
    public void InitializeBank(){
        List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
        //listeners.add(new Bank.PrintClientListener());
        //listeners.add(new Bank.EmailNotificationListener());
        bank = new Bank(listeners);
        bank.setName("Мой первый тестовый банк");
        client = new Client(Gender.MALE);
        client.setName("Testing Client");
        SavingAccount savingAccount = new SavingAccount((long) 1 , 450.0f);
        CheckingAccount checkingAccount = new CheckingAccount((long) 2, 450.0f, 1000.0f);
        client.addAccount(savingAccount);
        client.addAccount(checkingAccount);

    }

    @After
    public void FinalizeBank(){
        List<ClientRegistrationListener> listeners = null;
        bank = null;
        client = null;
        SavingAccount savingAccount = null;
        CheckingAccount checkingAccount = null;

    }

    @Test
    public void testDeposit() throws Exception {

    }

    @Test (expected = OverDraftLimitExceededException.class)
    public void testWithdraw() throws Exception {
    }

    @Test
    public void testGetTotalAccountOverdraft() throws Exception {
        client.getActiveAccount().withdraw(900.0f);
        /*\
        После списания со счета считаем общую сумму по всем счетам клиента и сравниваем
         */
        assertEquals(client.getActiveAccount().getTotalAccountOverdraft(), -450.0f, 0.0f);

    }
}