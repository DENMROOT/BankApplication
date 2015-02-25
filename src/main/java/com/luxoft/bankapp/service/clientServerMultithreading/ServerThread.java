package com.luxoft.bankapp.service.clientServerMultithreading;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.commanderCommands.*;
import com.luxoft.bankapp.service.services.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 06.02.2015.
 */
public class ServerThread implements Runnable {

    public static BankService myBankService = ServiceFactory.getBankServiceImpl();
    public static ClientService myClientService = ServiceFactory.getClientServiceImpl();
    public static AccountService myAccountService = ServiceFactory.getAccountServiceImpl();
    public final static Logger serverThreadLog = Logger.getLogger(ServerThread.class.getName());

    Socket server;
    static Command[] commands = {
            new GetClientBalance(), // 0
            new WithdrawCommand(), // 1
            new AddClientCommand(), //2
            new RemoveClientCommand(), //3
            new GetBankReportCommand() //4
    };

    /*
    Class container, contains currentBank and CurrentClient, for every ServerThread.
     */

    public class CurrentContainer {
        private Bank currentBank;
        private Client currentClient;

        public Bank getCurrentBank() {
            return currentBank;
        }

        public void setCurrentBank(Bank currentBank) {
            this.currentBank = currentBank;
        }

        public Client getCurrentClient() {
            return currentClient;
        }

        public void setCurrentClient(Client currentClient) {
            this.currentClient = currentClient;
        }
    }

    public ServerThread(Socket clientSocket) throws IOException
    {
        server = clientSocket;

    }

    public void run() {
        long start = new Date().getTime();
        String bankName = "My Bank";
        BankDAOImpl bankDao = DaoFactory.getBankDAO();
        CurrentContainer curContainer = new CurrentContainer();
        serverThreadLog.info(new Date() + " Пользователь подключился: " + server.toString());

        curContainer.setCurrentBank(bankDao.getBankByName(bankName));
        System.out.println("Bank ID:" + curContainer.getCurrentBank().getBankID()
                + " Bank Name: " + curContainer.getCurrentBank().getName());


        while (true) {
            try {
                BankServerThreaded.clientsCounter.decrementAndGet();
                System.out.println("Waiting for client on port " +
                        server.getLocalPort() + "...");

                System.out.println("Client connection received "
                        + server.getRemoteSocketAddress());
                DataInputStream in =
                        new DataInputStream(server.getInputStream());
                String clientCommand="";
                String [] clientCommandArgs={"","",};

                while (!clientCommandArgs[0].equals("EXIT")) {
                    clientCommand = in.readUTF();
                    clientCommandArgs = clientCommand.split("&");

                    switch (clientCommandArgs[0]) {
                        case "BankClient Get balance command": {
                            System.out.println("Get balance command received for client: " + clientCommandArgs[1]);
                            commands[0].execute_server(server.getOutputStream(), server, curContainer.getCurrentBank(), curContainer, clientCommandArgs);
                            break;
                        }
                        case "BankClient Withdrawal command": {
                            System.out.println("Withdrawal command received for client: " + clientCommandArgs[1]);
                            commands[1].execute_server(server.getOutputStream(), server, curContainer.getCurrentBank(), curContainer, clientCommandArgs);
                            break;
                        }
                        case "BankRemoteOffice add client command": {
                            System.out.println("Add client command received for client: " + clientCommandArgs[1]);
                            commands[2].execute_server(server.getOutputStream(), server, curContainer.getCurrentBank(), curContainer,clientCommandArgs);
                            break;
                        }
                        case "BankRemoteOffice delete client command": {
                            System.out.println("Delete client command received for client: " + clientCommandArgs[1]);
                            commands[3].execute_server(server.getOutputStream(), server, curContainer.getCurrentBank(), curContainer,clientCommandArgs);
                            break;
                        }
                        case "BankRemoteOffice get bank report command": {
                            System.out.println("Get Bank report command received: ");
                            commands[4].execute_server(server.getOutputStream(), server, curContainer.getCurrentBank(), curContainer,clientCommandArgs);
                            break;
                        }
                        default : break;
                    }
                }

                DataOutputStream out =
                        new DataOutputStream(server.getOutputStream());
                System.out.println("Exit command received from client ");
                out.writeUTF("Exit command received");
                return;
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                System.out.println("Коннекшн прерван в потоке Serverthread: " + e.getMessage());
                break;
            } finally {
                try {
                    long time = new Date().getTime()-start;
                    serverThreadLog.info(new Date() + " Пользователь отключился: " + server.toString() + " Время работы: " + time);
                    server.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
