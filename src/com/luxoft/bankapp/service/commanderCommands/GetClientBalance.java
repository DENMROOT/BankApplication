package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.services.BankServiceImpl;
import com.luxoft.bankapp.service.clientServer.BankServer;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Makarov Denis on 21.01.2015.
 */
public class GetClientBalance implements Command {

    @Override
    public void execute() {

    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {
        DataOutputStream outData = new DataOutputStream(out);
        Client client = null;
        try {
            client = BankCommander.myClientService.findClientByName(bank, clientCommandArg[1]);
            BankCommander.currentClient=client;
            System.out.println("Активный клиент установлен: ");
            System.out.println(BankCommander.currentClient);
            outData.writeUTF(clientCommandArg[1].toString() + " overall balance : " +  BankCommander.myClientService.getClientBalance(bank,client));
        } catch (IOException e) {
            try {
                outData.writeUTF(e.getMessage());
            } catch (IOException e1) {
                e1.getMessage();
            }
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get client balance");
    }
}
