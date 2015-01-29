package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public interface Command {
    void execute();
    void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg);
    void printCommandInfo();
}
