package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class RemoveClientCommand implements Command {

    public final static Logger removeClientCommangLog = Logger.getLogger(RemoveClientCommand.class.getName());

    @Override
    public void execute() {
        System.out.println("Введите имя клиента");
        Scanner paramScan = new Scanner(System.in);
        String clientName=paramScan.nextLine(); // initialize command with commandString

        Client removeClient = BankCommander.myClientService.findClientByName(BankCommander.currentBank, clientName);

        if (removeClient != null) {
            BankCommander.myClientService.deleteClient(BankCommander.currentBank, removeClient);
        } else {
            System.out.println("Указанный клиент не найден");
            removeClientCommangLog.severe("Указанный клиент не найден");
        }

    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, ServerThread.CurrentContainer currentContainer,String[] clientCommandArg) {
        DataOutputStream outData = new DataOutputStream(out);
        Client client = ServerThread.myClientService.findClientByName(bank, clientCommandArg[1]);
        try {
            ServerThread.myClientService.deleteClient(bank, client);
            System.out.println("Клиент удален: " + client.getName());
            outData.writeUTF("Клиент удален : " + client.getName());
        } catch (IOException e) {
            removeClientCommangLog.severe("Exception: " + e.getMessage());
            try {
                outData.writeUTF(e.getMessage());
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
                removeClientCommangLog.severe("Exception: " + e.getMessage());
            }
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Remove client by name command");
    }
}
