package com.luxoft.bankapp.service.clientServer;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankApplication;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.ClientRegistrationListener;
import com.luxoft.bankapp.service.clientServer.serverCommands.ServerCommand;
import com.luxoft.bankapp.service.clientServer.serverCommands.SocketServerGetClientBalance;
import com.luxoft.bankapp.service.clientServer.serverCommands.SosketServerWithdrawBalance;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    static ServerCommand[] commands = {
            new SocketServerGetClientBalance(), // 0
            new SosketServerWithdrawBalance() // 1
    };

    public BankServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        // закрытие мокета по таймауту
        // serverSocket.setSoTimeout(10000);
    }

    public void run() {
        listeners.add(new Bank.PrintClientListener());
        listeners.add(new Bank.EmailNotificationListener());
        Bank myBank = new Bank(listeners);
        myBank.setName("Мой тестовый банк номер 1");
        currentBank = myBank;

        myBankApplication.Initialize(currentBank,myBankService);

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
                String [] clientCommandArgs={"",""};

                while (!clientCommandArgs[0].equals("EXIT")) {
                    clientCommand = in.readUTF();
                    clientCommandArgs = clientCommand.split("&");
                    //System.out.println(clientCommand);
                    DataOutputStream out =
                            new DataOutputStream(server.getOutputStream());
                    switch (clientCommandArgs[0]) {
                        case "BankClient Get balance command": {
                            System.out.println("Get balance command received for client: " + clientCommandArgs[1]);
                            commands[0].execute(out, server, myBank,  clientCommandArgs[1]);
                            break;
                        }
                        case "BankClient Withdrawal command": {
                            System.out.println("Withdrawal command received for client: " + clientCommandArgs[1]);
                            commands[1].execute(out, server, myBank, clientCommandArgs[1]);
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
