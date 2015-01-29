package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.services.BankServiceImpl;
import com.luxoft.bankapp.service.clientServer.BankServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class WithdrawCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Введите сумму списания");
        Scanner paramScan = new Scanner(System.in);
        String param=paramScan.nextLine(); // initialize command with commandString
        Float withdrawalAmount = Float.valueOf(param);

        try {
            BankCommander.myAccountService.withdrawFromAccount(BankCommander.currentClient,BankCommander.currentClient.getActiveAccount(), withdrawalAmount);
            System.out.println("Новый баланс по счету" + BankCommander.currentClient.getActiveAccount());
        } catch (NotEnoughFundsException e) {
            System.out.println("Ошибка при списании средств" + e.getMessage());
        }
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {
        DataOutputStream outData = new DataOutputStream(out);
        try {
            Account activeAccount = BankServer.currentClient.getActiveAccount();
            System.out.println("Списание средств со счета: ");
            System.out.println(activeAccount);
            try {
                BankCommander.myAccountService.withdrawFromAccount(BankServer.currentClient, activeAccount, Float.valueOf(clientCommandArg[1]));
            } catch (NotEnoughFundsException e) {
                System.out.println("Ошибка при списании средств: " + e.getMessage());
                outData.writeUTF("Ошибка при списании средств: " + e.getMessage());
            }
            System.out.println("Новый баланс по счету: ");
            System.out.println(activeAccount);
            outData.writeUTF("New overall balance : " + BankCommander.myClientService.getClientBalance(bank, BankServer.currentClient));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw funds from balance command");
    }
}
