package com.luxoft.bankapp.service.clientServer.serverCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.clientServer.BankServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Makarov Denis on 21.01.2015.
 */
public class SocketServerGetClientBalance implements ServerCommand {
    @Override
    public void execute(DataOutputStream out, Socket server, Bank bank, String clientCommandArg) {
        Client client = null;
        try {
            BankServiceImpl myBankService = new BankServiceImpl();
            client = myBankService.getClientByName(bank, clientCommandArg);
            BankServer.currentClient = client;
            System.out.println("Активный клиент установлен: ");
            System.out.println(BankServer.currentClient);
            out.writeUTF(clientCommandArg.toString() + " overall balance : " + myBankService.getClientBalance(bank,client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get client balance");
    }
}
