package com.luxoft.bankapp.service;

import java.util.Scanner;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class FindClientCommand implements Command {
    @Override
    public void execute() {

        System.out.println("Введите имя клиента");

        Scanner paramScan = new Scanner(System.in);
        String clientName=paramScan.nextLine(); // initialize command with commandString

            BankCommander.currentClient=BankCommander.myBankService.findClient(BankCommander.currentBank, clientName);
            if (BankCommander.currentClient!=null) {
                System.out.println("Клиент найден");
                System.out.println("Текущий клиент установлен: " + "\n" + BankCommander.currentClient.toString());
            }
            else System.out.println("Указанный клиент отсутствует");
    }

    @Override
    public void printCommandInfo() {

    }
}
