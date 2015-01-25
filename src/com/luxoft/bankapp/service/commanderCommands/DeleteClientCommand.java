package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.Command;
import com.luxoft.bankapp.service.clientServer.BankServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Денис on 25.01.2015.
 */
public class DeleteClientCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {
        DataOutputStream outData = new DataOutputStream(out);
        Client client = BankServer.myBankService.getClientByName(BankServer.currentBank, clientCommandArg[1]);
        try {
            BankServer.myBankService.deleteClient(BankServer.currentBank, client);
            System.out.println("Клиент удален: " + client.getName());
            outData.writeUTF("Клиент удален : " + client.getName());
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

    }
}
