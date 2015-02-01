package com.luxoft.bankapp.service.clientServer.clientCommands;

import com.luxoft.bankapp.model.BankInfo;
import com.luxoft.bankapp.model.BankReportContainer;

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
        BankInfo receivedBankInfo = null;
        try {
            inFromServer = client.getInputStream();
            ObjectInputStream inObj = new ObjectInputStream(inFromServer);
            try {
                receivedBankInfo = (BankInfo) inObj.readObject();
            } catch (ClassNotFoundException e) {
                e.getMessage();
            }
            System.out.println("Server says: Отчет BankInfo получен");
            System.out.println("Количество клиентов банка: " + receivedBankInfo.getNumberOfClients());
            System.out.println("Сумма по счетам банка: " + receivedBankInfo.getTotalAccountSum());
            System.out.println("Клиенты банка по городам: " + "\n" + receivedBankInfo.getClientsByCity());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get bank report");
    }
}
