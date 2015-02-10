package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.ClientDAOImpl;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.services.AccountServiceImpl;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 29.01.2015.
 */
public class SelectActiveAccount implements Command {

    Logger selectActiveAccountCommangLog = Logger.getLogger("SelectActiveAccount");

    @Override
    public void execute() {
        System.out.println("Список счетов клиента");
        System.out.println(BankCommander.currentClient);

        System.out.println("Выберите ID активного счета");
        Scanner paramScan = new Scanner(System.in);
        Long accountID =Long.valueOf(paramScan.nextLine()); // initialize command with commandString

        AccountServiceImpl accountService = AccountServiceImpl.getInstance();
        Account activeAccount =
                accountService.getInstance().findAccountByID(BankCommander.currentClient, accountID);

        if (activeAccount != null) {
            BankCommander.currentClient.setActiveAccount(activeAccount);
            System.out.println("Текущим счетом выбран: " + activeAccount);
        } else {
            System.out.println("Номер счета выбран неверно");
            selectActiveAccountCommangLog.severe("Номер счета выбран неверно");
        };

    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, ServerThread.CurrentContainer currentContainer, String[] clientCommandArg) {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Select active account command");

    }
}
