package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.main.BankCommander;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class TransferCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Введите имя клиента получателя");
        Scanner paramScan = new Scanner(System.in);
        String param=paramScan.nextLine(); // initialize command with commandString
        String recClientName = param;

        System.out.println("Введите сумму перевода");
        param=paramScan.nextLine(); // initialize command with commandString
        Float transitAmount = Float.valueOf(param);

        System.out.println("Введите ID счета получателя");
        param=paramScan.nextLine(); // initialize command with commandString
        long accountToID = Long.valueOf(param);


        Account accountFrom = BankCommander.currentClient.getActiveAccount();
        Client clientTo = BankCommander.myClientService.findClientByName(BankCommander.currentBank, recClientName);

        if (clientTo != null) {
            Account accountTo = BankCommander.myAccountService.findAccountByID(
                   clientTo , accountToID);
            try {
                BankCommander.myAccountService.transferFunds(accountFrom, accountTo, transitAmount);
                System.out.println("Новый баланс по счету: " + BankCommander.currentClient);
                System.out.println("Новый баланс по счету: " + BankCommander.myClientService.findClientByName(
                        BankCommander.currentBank, recClientName));
            } catch (NotEnoughFundsException e) {
                System.out.println("Ошибка при списании средств" + e.getMessage());
            }
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
