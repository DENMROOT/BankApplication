package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.Command;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class GetAccountsCommand implements Command {
    @Override
    public void execute() {
        Set<Account> clientAccounts = new HashSet<Account>();

        clientAccounts= BankCommander.myBankService.getClientAccounts(BankCommander.currentClient);

        for (Account accountIterator : clientAccounts) {
            System.out.println(accountIterator.toString());
        }
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get all client accounts command");
    }
}
