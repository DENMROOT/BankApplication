package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Denis Makarov on 24.02.2015.
 */
public class ExitCommand implements Command{
    public void execute() {
        System.exit(0);
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank,
                               ServerThread.CurrentContainer currentContainer, String[] clientCommandArg) {

    }

    public void printCommandInfo() {
        System.out.println("Exit");
    }
}
