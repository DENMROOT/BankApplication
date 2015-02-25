package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;
import com.luxoft.bankapp.service.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.services.BankServiceImpl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class WithdrawCommand implements Command {

    public final static Logger withdrawCommangLog = Logger.getLogger(WithdrawCommand.class.getName());

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
            withdrawCommangLog.severe("Exception: " + e.getMessage());
        }
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, ServerThread.CurrentContainer currentContainer, String[] clientCommandArg) {
        DataOutputStream outData = new DataOutputStream(out);
            try {
                Account activeAccount = currentContainer.getCurrentClient().getActiveAccount();
                System.out.println("Списание средств со счета: ");
                System.out.println(activeAccount);
                try {
                    ServerThread.myAccountService.withdrawFromAccount(currentContainer.getCurrentClient(), activeAccount, Float.valueOf(clientCommandArg[1]));
                } catch (NotEnoughFundsException e) {
                    System.out.println("Ошибка при списании средств: " + e.getMessage());
                    withdrawCommangLog.severe("Exception: " + e.getMessage());
                    outData.writeUTF("Ошибка при списании средств: " + e.getMessage());
                }
                System.out.println("Новый баланс по счету: ");
                System.out.println(activeAccount);
                outData.writeUTF("New overall balance : " + ServerThread.myClientService.getClientBalance(bank, currentContainer.getCurrentClient()));
            } catch (IOException e) {
                System.out.println("IO Exception: " + e.getMessage());
                withdrawCommangLog.severe("Exception: " + e.getMessage());
            }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw funds from balance command");
    }
}
