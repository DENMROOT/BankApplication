package com.luxoft.bankapp.service.clientServer;

import com.luxoft.bankapp.main.BankApplication;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.ClientRegistrationListener;
import com.luxoft.bankapp.service.DAO.BankDAOImpl;
import com.luxoft.bankapp.service.commanderCommands.*;
import com.luxoft.bankapp.service.services.BankServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makarov Denis on 21.01.2015.
 */

public class BankServer extends Thread {

    public static BankApplication myBankApplication = new BankApplication ();
    public static BankServiceImpl myBankService = new BankServiceImpl();
    public static Bank currentBank;
    public static Client currentClient;
    List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();


    private ServerSocket serverSocket;
    static Command[] commands = {
            new GetClientBalance(), // 0
            new WithdrawCommand(), // 1
            new AddClientCommand(), //2
            new RemoveClientCommand(), //3
            new GetBankReportCommand() //4
    };

    public BankServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        // закрытие мокета по таймауту
        // serverSocket.setSoTimeout(10000);
    }

    public void run() {
        String bankName = "My Bank";
        BankDAOImpl bankDao = new BankDAOImpl();
        //currentBank.setName(bankName);
        currentBank = bankDao.getBankByName(bankName);
        BankCommander.currentBank = currentBank;

        System.out.println("Bank ID:" + currentBank.getBankID() + " Bank Name: " + currentBank.getName());
        //myBankApplication.Initialize(currentBank,myBankService);

        //myBankApplication.Initialize(currentBank,myBankService);

        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Client connection received "
                        + server.getRemoteSocketAddress());
                DataInputStream in =
                        new DataInputStream(server.getInputStream());
                String clientCommand="";
                String [] clientCommandArgs={"","",};

                while (!clientCommandArgs[0].equals("EXIT")) {
                    clientCommand = in.readUTF();
                    clientCommandArgs = clientCommand.split("&");
                    //System.out.println(clientCommand);

                    switch (clientCommandArgs[0]) {
                        case "BankClient Get balance command": {
                            System.out.println("Get balance command received for client: " + clientCommandArgs[1]);
                            commands[0].execute_server(server.getOutputStream(), server, currentBank, clientCommandArgs);
                            break;
                        }
                        case "BankClient Withdrawal command": {
                            System.out.println("Withdrawal command received for client: " + clientCommandArgs[1]);
                            commands[1].execute_server(server.getOutputStream(), server, currentBank, clientCommandArgs);
                            break;
                        }
                        case "BankRemoteOffice add client command": {
                            System.out.println("Add client command received for client: " + clientCommandArgs[1]);
                            commands[2].execute_server(server.getOutputStream(), server, currentBank, clientCommandArgs);
                            break;
                        }
                        case "BankRemoteOffice delete client command": {
                            System.out.println("Delete client command received for client: " + clientCommandArgs[1]);
                            commands[3].execute_server(server.getOutputStream(), server, currentBank, clientCommandArgs);
                            break;
                        }
                        case "BankRemoteOffice get bank report command": {
                            System.out.println("Get Bank report command received: ");
                            commands[4].execute_server(server.getOutputStream(), server, currentBank, clientCommandArgs);
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
                System.exit(0);
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        int port = 4444;
        try
        {
            Thread t = new BankServer(port);
            t.start();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
