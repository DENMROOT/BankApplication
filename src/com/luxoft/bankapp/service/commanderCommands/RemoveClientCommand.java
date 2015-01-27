package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.ClientNotFoundException;
import com.luxoft.bankapp.service.Command;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class RemoveClientCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Введите имя клиента");
        Scanner paramScan = new Scanner(System.in);
        String clientName=paramScan.nextLine(); // initialize command with commandString
        ClientDAOImpl clientDao = new ClientDAOImpl();
        try {
            Client client = clientDao.findClientByName(BankCommander.currentBank, clientName);
            clientDao.remove(client);
        } catch (ClientNotFoundException e) {
            e.getMessage();
        }
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Remove client by name command");
    }
}
