package com.luxoft.bankapp.service.clientServer.clientCommands;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Денис on 25.01.2015.
 */
public class SocketRemoteOfficeDeleteClientCommand  implements ClientCommand{
    @Override
    public void execute(DataOutputStream out, Socket client) {
        try {
            System.out.println("Введите имя клиента");
            Scanner paramScan = new Scanner(System.in);
            String clientName=paramScan.nextLine(); // initialize command with commandString

            out.writeUTF("BankRemoteOffice delete client command" + "&" + clientName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inFromServer = null;
        try {
            inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("Server says: " + in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Delete existing client");
    }
}
