package com.luxoft.bankapp.service.clientServer;

import com.luxoft.bankapp.service.clientServer.clientCommands.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Денис on 25.01.2015.
 */
public class BankRemoteOffice {
    static ClientCommand[] commands = {
            new SocketClientGetClientBalance(), // 0
            new SocketRemoteOfficeAddClientCommand(), // 1
            new SocketRemoteOfficeDeleteClientCommand(), // 2
            new SocketRemoteOfficeGetBankReportCommand(), // 3
            new ClientCommand() { // 4 - Exit Command
                public void execute(DataOutputStream out, Socket client) {
                    try {
                        out.writeUTF("EXIT" + "&" + "null");
                        InputStream inFromServer = client.getInputStream();
                        DataInputStream in = new DataInputStream(inFromServer);
                        System.out.println("Server says " + in.readUTF());
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }

                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 4444;
        try
        {
            System.out.println("Connecting to " + serverName
                    + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out =
                    new DataOutputStream(outToServer);

            while (true) {
                System.out.println("");
                for (int i=0;i<commands.length;i++) { // show menu
                    System.out.print(i+") ");
                    commands[i].printCommandInfo();
                }
                System.out.println("Choose command :");
                Scanner sc = new Scanner(System.in);
                int command=sc.nextInt(); // initialize command with commandString
                commands[command].execute(out, client);
            }


        }catch(IOException e)
        {
            System.out.println("Соединение с сервером не установлено: " + e.getMessage());
        }
    }
}
