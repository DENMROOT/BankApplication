package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.exceptions.ClientExcistsException;
import org.junit.Before;
import org.junit.Test;
import service.TestService;

import static org.junit.Assert.assertTrue;

/**
 * Created by Makarov Denis on 02.02.2015.
 */
public class TestServiceTest {
    Bank bank1;
    Bank bank2;

    @Before
    public void InitBanks (){
        bank1 = new Bank ();
        bank1.setBankID(1);
        bank1.setName("My Bank");
        Client client = new Client (Gender.MALE);
        client.setName("Ivan Ivanov");
        client.setCity("Kiev");
        // add some fields from Client
        // marked as @NoDB, with different values
        // for client and client2
        client.addAccount(new CheckingAccount (1, 0.0f, 100.0f));

        Client client1 = new Client (Gender.FEMALE);
        client1.setName("Masha Petrova");
        client1.setCity("Lviv");
        // add some fields from Client
        // marked as @NoDB, with different values
        // for client and client2
        client1.addAccount(new CheckingAccount (1, 40.0f, 500.0f));
        try {
            bank1.addClient(client);
            bank1.addClient(client1);
        } catch (ClientExcistsException e) {
            System.out.println(e.getMessage());
        }

        bank2 = new Bank();
        bank2.setBankID(2);
        bank2.setName("My Bank");
        Client client2 = new Client (Gender.MALE);
        client2.setName("Ivan Ivanov");
        client2.setCity("Kiev");
        client2.addAccount(new CheckingAccount (1, 0.0f, 100.0f));

        Client client3 = new Client (Gender.FEMALE);
        client3.setName("Masha Petrova");
        client3.setCity("Lviv");
        // add some fields from Client
        // marked as @NoDB, with different values
        // for client and client2
        client3.addAccount(new CheckingAccount (1, 40.0f, 500.0f));
        try {
            bank2.addClient(client2);
            bank2.addClient(client3);
        } catch (ClientExcistsException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testEquals() {
    assertTrue(TestService.isEquals(bank1, bank2));
    }
}
