package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;
import com.luxoft.bankapp.service.services.BankServiceImpl;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 21.01.2015.
 */
public class GetClientBalance implements Command {

    Logger getClientBalanceCommangLog = Logger.getLogger("GetClientBalance");

    @Override
    public void execute() {

    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, ServerThread.CurrentContainer curContainer, String[] clientCommandArg) {
        DataOutputStream outData = new DataOutputStream(out);
        Client client = null;
            client = ServerThread.myClientService.findClientByName(bank, clientCommandArg[1]);

            if (client == null) {
                try {
                    outData.writeUTF("Клиент: " + clientCommandArg[1] + " не найден" );
                } catch (IOException e) {
                    getClientBalanceCommangLog.severe("Exception: " + e.getMessage());
                    try {
                        outData.writeUTF(e.getMessage());
                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                        getClientBalanceCommangLog.severe("Exception: " + e1.getMessage());
                    }
                }
            } else {
                try {
                    curContainer.setCurrentClient(client);
                    System.out.println("Активный клиент установлен: ");
                    System.out.println(curContainer.getCurrentClient());
                    outData.writeUTF(clientCommandArg[1].toString() + " overall balance : " +  BankCommander.myClientService.getClientBalance(bank,client));
                } catch (IOException e) {
                    getClientBalanceCommangLog.severe("Exception: " + e.getMessage());
                    try {
                        outData.writeUTF(e.getMessage());
                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                        getClientBalanceCommangLog.severe("Exception: " + e1.getMessage());
                    }
                }
            }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get client balance");
    }
}
