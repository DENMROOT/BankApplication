package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Makarov Denis on 19.01.2015.
 */
public class ShowHelpCommand implements Command {
    @Override
    public void execute() {
        System.out.println("1 - Find client, input client name");
        System.out.println("2 - Get accounts command, list of accounts for active user");
        System.out.println("3 - Withdraw command, withdraw sum from active account of a client");
        System.out.println("3 - Deposit command, deposit sum to active account of a client");
        System.out.println("4 - Transfer sum, transfer sum between accounts of a client");
        System.out.println("5 - Add Client command, add new client to a bank");
        System.out.println("6 - Show Help command, show help about interface");
        System.out.println("7 - exit");

    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, ServerThread.CurrentContainer currentContainer, String[] clientCommandArg) {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Show help command");

    }
}
