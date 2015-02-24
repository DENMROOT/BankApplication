package com.luxoft.bankapp.main;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.clientServerMultithreading.ServerThread;
import com.luxoft.bankapp.service.services.*;
import com.luxoft.bankapp.service.commanderCommands.Command;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.commanderCommands.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class BankCommander{
    public static BankServiceImpl myBankService = ServiceFactory.getBankServiceImpl();
    public static ClientService myClientService;
    public static AccountService myAccountService;
    public static String bankName = "My Bank";
    public static Bank currentBank;
    public static Client currentClient;
    protected static final String APPLICATION_CONTEXT_XML_FILE_NAME = "classpath:application-context.xml";
    private static AbstractApplicationContext context;
    private static Map <String, Command> commandsMap;

    //public static Map<String, Command> commandsMap = new TreeMap<String, Command>();
    /*
    static Command[] commands = {
            new FindClientCommand(), // 0
            new GetAccountsCommand(), // 1
            new SelectActiveAccount(), //2
            new WithdrawCommand(), // 3
            new DepositCommand(), // 4
            new TransferCommand(), // 5
            new AddClientCommand(), // 6
            new RemoveClientCommand(), // 7
            new ShowHelpCommand(), // 8
            new GetBankReportCommand(), // 9
            new ExitCommand()
    };
    */

    public void setCommandsMap(Map commandsMap) {
        this.commandsMap = commandsMap;
    }

    public Map getCommandsMap() {
        return commandsMap;
    }

    public static void main(String args[]) {

        context = new FileSystemXmlApplicationContext(
                new String[] { APPLICATION_CONTEXT_XML_FILE_NAME });
        currentBank = context.getBean(BankDAOImpl.class).getBankByName(bankName);
        myClientService = context.getBean(ClientServiceImpl.class);
        myAccountService = context.getBean(AccountServiceImpl.class);

        System.out.println("Bank ID:" + currentBank.getBankID() + " Bank Name: " + currentBank.getName());

        while (true) {
            for(Map.Entry<String, Command> e : commandsMap.entrySet()) {
                System.out.print(e.getKey() + ". ");
                e.getValue().printCommandInfo();
            }

            Scanner sc = new Scanner(System.in);
            String command=sc.nextLine(); // initialize command with commandString
            for(Map.Entry<String, Command> e : commandsMap.entrySet()) {
                if (e.getKey().equals(command)) {
                    e.getValue().execute();
                }
            }

        }
    }


}
