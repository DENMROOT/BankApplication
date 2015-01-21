package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.commanderCommands.*;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class BankCommander{

    public static BankApplication myBankApplication = new BankApplication ();
    public static BankServiceImpl myBankService = new BankServiceImpl();

    public static Bank currentBank = new Bank();
    public static Client currentClient;
    public static Map<String, Command> commandsMap = new TreeMap<String, Command>();

    static Command[] commands = {
            new FindClientCommand(), // 0
            new GetAccountsCommand(), // 1
            new WithdrawCommand(), // 2
            new DepositCommand(), // 3
            new TransferCommand(), // 4
            new AddClientCommand(), // 5
            new ShowHelpCommand(), //6
            new Command() { // 7 - Exit Command
                public void execute() {
                    System.exit(0);
                }

                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public void registerCommand(String name, Command command){
        commandsMap.put(name, command);
    }

    public void removeCommand(String name){
        commandsMap.remove(name);
    }

    public static void main(String args[]) {

        myBankApplication.Initialize(currentBank,myBankService);

        while (true) {
            for (int i=0;i<commands.length;i++) { // show menu
                System.out.print(i+") ");
                commands[i].printCommandInfo();
            }

            Scanner sc = new Scanner(System.in);
            int command=sc.nextInt(); // initialize command with commandString
            commands[command].execute();
        }
    }

}
