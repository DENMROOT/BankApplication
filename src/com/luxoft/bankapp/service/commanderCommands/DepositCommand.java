package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.Command;
import com.luxoft.bankapp.service.DAO.AccountDAOImpl;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class DepositCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Введите сумму депозита");
        Scanner paramScan = new Scanner(System.in);
        String param=paramScan.nextLine(); // initialize command with commandString
        Float depositSum = Float.valueOf(param);
        AccountDAOImpl accountDAO = new AccountDAOImpl();

        BankCommander.myBankService.depositToAccount(BankCommander.currentClient, BankCommander.currentClient.getActiveAccount(), depositSum);
        accountDAO.save(BankCommander.currentClient.getActiveAccount());
        System.out.println("Новый баланс по счету" + BankCommander.currentClient.getActiveAccount());

    }

    @Override
    public void execute_server (OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit funds command");
    }
}
