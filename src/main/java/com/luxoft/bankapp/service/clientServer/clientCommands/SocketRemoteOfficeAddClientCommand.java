package com.luxoft.bankapp.service.clientServer.clientCommands;

import com.luxoft.bankapp.model.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Денис on 25.01.2015.
 */
public class SocketRemoteOfficeAddClientCommand  implements ClientCommand {
    @Override
    public void execute(DataOutputStream out, Socket client) {
        try {
            System.out.println("Введите имя клиента");
            Scanner paramScan = new Scanner(System.in);
            String clientName=paramScan.nextLine(); // initialize command with commandString

            System.out.println("Введите пол клиента (f/m)");
            paramScan = new Scanner(System.in);
            String gender=paramScan.nextLine(); // initialize command with commandString

            System.out.println("Введите город проживания");
            paramScan = new Scanner(System.in);
            String city=paramScan.nextLine(); // initialize command with commandString

            System.out.println("Введите email клиента (x..x@x..x)");
            paramScan = new Scanner(System.in);
            String email=paramScan.nextLine(); // initialize command with commandString

            System.out.println("Введите телефон клиента ((XXX)XXXXXXX)");
            paramScan = new Scanner(System.in);
            String phone=paramScan.nextLine(); // initialize command with commandString

            System.out.println("Введите начальный овердрафт");
            paramScan = new Scanner(System.in);
            String initialOverdraft = paramScan.nextLine(); // initialize command with commandString

            out.writeUTF("BankRemoteOffice add client command" +
                    "&" + clientName +
                    "&" + gender +
                    "&" + city +
                    "&" + email +
                    "&" + phone +
                    "&" + initialOverdraft);

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
        System.out.println("Add new client");
    }
}
