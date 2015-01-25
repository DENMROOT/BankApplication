package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.NotEnoughFundsException;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.Command;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class TransferCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Введите сумму перевода");
        Scanner paramScan = new Scanner(System.in);
        String param=paramScan.nextLine(); // initialize command with commandString
        Float transitAmount = Float.valueOf(param);

        System.out.println("Введите ID счета источника");
        param=paramScan.nextLine(); // initialize command with commandString
        long accountFromID = Long.valueOf(param);

        System.out.println("Введите ID счета получателя");
        param=paramScan.nextLine(); // initialize command with commandString
        long accountToID = Long.valueOf(param);

        Account accountFrom = BankCommander.myBankService.findAccountByID(BankCommander.currentClient, accountFromID);
        Account accountTo = BankCommander.myBankService.findAccountByID(BankCommander.currentClient, accountToID);

        try {
            BankCommander.myBankService.withdrawFromAccount(BankCommander.currentClient, accountFrom, transitAmount);
            BankCommander.myBankService.depositToAccount(BankCommander.currentClient, accountTo, transitAmount);
            System.out.println("Новый баланс по счету: " + accountFrom);
            System.out.println("Новый баланс по счету: " + accountTo);
        } catch (NotEnoughFundsException e) {
            System.out.println("Ошибка при списании средств" + e.getMessage());
        }
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer funds from account to account command");
    }
}
