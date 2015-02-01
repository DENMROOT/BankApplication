package com.luxoft.bankapp.main;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.model.BankReport;
import com.luxoft.bankapp.service.services.BankServiceImpl;
import com.luxoft.bankapp.model.ClientRegistrationListener;
import com.luxoft.bankapp.service.bankFeeder.BankFeedService;

import java.util.*;

/**
 * Created by Makarov Denis on 14.01.2015.
 */
public class BankApplication {
    public static void main (String [] args){
        BankServiceImpl myBankService = new BankServiceImpl();

        List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
        listeners.add(new Bank.PrintClientListener());
        listeners.add(new Bank.EmailNotificationListener());
        Bank myBank = new Bank(listeners);
        myBank.setName("My Bank");

        System.out.println("Working");
        System.out.println("Инициализация данных");
        Initialize(myBank, myBankService );
        //printBankReport(myBank);
        //System.out.println("Модификация данных");
        //modifyBank(myBank, myBankService);
        //printBankReport(myBank);

        if (args[0].equals("report")) printBankReport(myBank);
    }

    public static void Initialize(Bank bank,BankServiceImpl myBankService ){
        //инициализация с помощью feed файлов
        BankFeedService myBankFeedService = new BankFeedService(bank);
        myBankFeedService.loadFeeds("E:\\PROGRAMMING\\IntellijIDEA\\Projects\\BankApplication\\feed");

        /*
        тестовая сериализация
         */
        /*
        try {
            Client tempSaveClient;
            myBankService.saveClient(tempSaveClient = myBankService.getClientByName(bank, "Vasya Ivanov"));
            System.out.println("Клиент " + tempSaveClient.getName() + " сохранен" );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Client tempLoadClient;
            tempLoadClient = myBankService.loadClient();
            System.out.println("Клиент " + tempLoadClient.getName() + " загружен" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        /* тестовое получение данных по клиенту, используя его имя
        System.out.println("Клиент получен по Map из банка :" + myBankService.getClientByName(bank, "Makarov Denis"));
        */

        /* Вывод данных клиента по toString()
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println(myClient1);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
         */

        //myBankService.removeClient(bank,myClient1);
    }

    private static void modifyBank(Bank bank, BankServiceImpl myBankService){
    }

    private static void printBankReport(Bank bank){

        bank.printReport();
        BankReport myBankReport = new BankReport();

        System.out.println("Количество клиентов банка ***" + bank.getName() + "*** : " + myBankReport.getNumberOfClients(bank));
        System.out.println("Общее количество счетов банка ***" + bank.getName() + "*** : " + myBankReport.getAccountsNumber(bank));
        System.out.println("Отсортированный список клиентов, банка***" + bank.getName() + "*** : \n" + myBankReport.getClientsSorted(bank));
        System.out.println("Суммарный овердрафт, банка ***" + bank.getName() + "*** : \n" + myBankReport.getBankCreditSum(bank));
        System.out.println("Клиенты банка по городам :");
        myBankReport.getClientsByCity(bank);
    }

}
