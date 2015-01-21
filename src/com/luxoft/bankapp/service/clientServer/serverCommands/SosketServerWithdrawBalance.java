package com.luxoft.bankapp.service.clientServer.serverCommands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.NotEnoughFundsException;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.clientServer.BankServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Makarov Denis on 21.01.2015.
 */
public class SosketServerWithdrawBalance implements ServerCommand {
    @Override
    public void execute(DataOutputStream out, Socket server, Bank bank, String clientCommandArg) {
        try {
            BankServiceImpl myBankService = new BankServiceImpl();
            Client client = BankServer.currentClient;
            Account activeAccount = client.getActiveAccount();
            System.out.println("Списание средств со счета: ");
            System.out.println(activeAccount);
            try {
                myBankService.withdrawFromAccount(client, activeAccount, Float.valueOf(clientCommandArg));
            } catch (NotEnoughFundsException e) {
                System.out.println("Ошибка при списании средств: " + e.getMessage());
                out.writeUTF("Ошибка при списании средств: " + e.getMessage());
            }
            System.out.println("Новый баланс по счету: ");
            System.out.println(activeAccount);
            out.writeUTF("New overall balance : " + myBankService.getClientBalance(bank,client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw funds from balance");
    }
}
