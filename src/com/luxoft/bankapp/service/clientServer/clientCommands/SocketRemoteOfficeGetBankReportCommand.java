package com.luxoft.bankapp.service.clientServer.clientCommands;

import com.luxoft.bankapp.service.BankReportContainer;

import java.io.*;
import java.net.Socket;

/**
 * Created by Денис on 25.01.2015.
 */
public class SocketRemoteOfficeGetBankReportCommand implements ClientCommand{
    @Override
    public void execute(DataOutputStream out, Socket client) {
        try {
            out.writeUTF("BankRemoteOffice get bank report command");

        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inFromServer = null;
        BankReportContainer receivedBankReportContainer = null;
        try {
            inFromServer = client.getInputStream();
            ObjectInputStream inObj = new ObjectInputStream(inFromServer);
            try {
               receivedBankReportContainer = (BankReportContainer) inObj.readObject();
            } catch (ClassNotFoundException e) {
                e.getMessage();
            }
            System.out.println("Server says: Отчет BankReport получен");
            System.out.println("Количество клиентов банка: " + receivedBankReportContainer.getNumberOfClients());
            System.out.println("Количество счетов банка: " + receivedBankReportContainer.getAccountsNumber());
            System.out.println("Отсортированные клиенты банка: " + "\n" + receivedBankReportContainer.getClientsSorted());
            System.out.println("Общий овердрафт по банку: " + receivedBankReportContainer.getBankCreditSum());
            System.out.println("Клиенты банка по городам: " + "\n" + receivedBankReportContainer.getClientsByCity());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get bank report");
    }
}
