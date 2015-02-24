package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public interface Command {
    void execute();
    void execute_server(OutputStream out, Socket server, Bank bank, ServerThread.CurrentContainer curContainer, String[] clientCommandArg);
    void printCommandInfo();
}
