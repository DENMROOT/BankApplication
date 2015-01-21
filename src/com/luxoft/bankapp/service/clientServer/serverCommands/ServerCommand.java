package com.luxoft.bankapp.service.clientServer.serverCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public interface ServerCommand {
    void execute(DataOutputStream out, Socket server, Bank bank, String clientCommandArg);
    void printCommandInfo();
}
