package com.luxoft.bankapp.service.clientServerMultithreading;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.DAO.DaoFactory;
import com.luxoft.bankapp.service.commanderCommands.*;
import com.luxoft.bankapp.service.services.AccountServiceImpl;
import com.luxoft.bankapp.service.services.BankServiceImpl;
import com.luxoft.bankapp.service.services.ClientServiceImpl;
import com.luxoft.bankapp.service.services.ServiceFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Makarov Denis on 06.02.2015.
 */
public class ServerThread implements Runnable {

    public static BankServiceImpl myBankService = ServiceFactory.getBankServiceImpl();
    public static ClientServiceImpl myClientService = ServiceFactory.getClientServiceImpl();
    public static AccountServiceImpl myAccountService = ServiceFactory.getAccountServiceImpl();

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
        String bankName = "My Bank";
        BankDAOImpl bankDao = DaoFactory.getBankDAO();
        CurrentContainer curContainer = new CurrentContainer();
        curContainer.setCurrentBank(bankDao.getBankByName(bankName));
        System.out.println("Bank ID:" + curContainer.getCurrentBank().getBankID()
                + " Bank Name: " + curContainer.getCurrentBank().getName());

        while (true) {
            try {
                BankServerThreaded.setClientsCounter(BankServerThreaded.getClientsCounter()-1);
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
                server.close();
                return;
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                System.out.println("Коннекшн прерван: " + e.getMessage());
                break;
            }
        }
    }
}
