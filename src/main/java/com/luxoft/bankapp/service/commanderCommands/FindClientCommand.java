package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class FindClientCommand implements Command {
    public final static Logger findClientCommangLog = Logger.getLogger(FindClientCommand.class.getName());

    @Override
    public void execute() {

        System.out.println("Введите имя клиента");

        Scanner paramScan = new Scanner(System.in);
        String clientName=paramScan.nextLine(); // initialize command with commandString

            BankCommander.currentClient=BankCommander.myClientService.findClientByName(BankCommander.currentBank, clientName);
            if (BankCommander.currentClient!=null) {
                System.out.println("Клиент найден");
                System.out.println("Текущий клиент установлен: " + "\n" + BankCommander.currentClient.toString());
            } else {
                findClientCommangLog.severe("Указанный клиент не найден");
            }
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, ServerThread.CurrentContainer currentContainer, String[] clientCommandArg) {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Find client by name command");
    }
}
