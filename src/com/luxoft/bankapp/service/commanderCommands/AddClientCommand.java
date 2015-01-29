package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.exceptions.ClientExcistsException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Makarov Denis on 15.01.2015.
 */
public class AddClientCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Введите имя клиента");
        Scanner paramScan = new Scanner(System.in);
        String clientName=paramScan.nextLine(); // initialize command with commandString

        System.out.println("Введите пол клиента (f/m)");
        paramScan = new Scanner(System.in);
        String gender=paramScan.nextLine(); // initialize command with commandString
        Client client = null;

        System.out.println("Введите email клиента (X..X@X..X.XXX)");
        paramScan = new Scanner(System.in);
        String email=paramScan.nextLine(); // initialize command with commandString

        System.out.println("Введите телефон клиента ((XXX)XXXXXXX)");
        paramScan = new Scanner(System.in);
        String phone=paramScan.nextLine(); // initialize command with commandString

        System.out.println("Введите город клиента");
        paramScan = new Scanner(System.in);
        String city=paramScan.nextLine(); // initialize command with commandString

        System.out.println("Введите начальный овердрафт");
        paramScan = new Scanner(System.in);
        float initialOverdraft= Float.valueOf(paramScan.nextLine()); // initialize command with commandString

        switch (gender) {
            case "m":
                client = new Client(Gender.MALE);
                break;
            case "f":
                client = new Client(Gender.FEMALE);
                break;
            default:
                System.out.println("Некорректно задан пол клиента");
        }
        client.setName(clientName);
        client.setEmail(email);
        client.setPhone(phone);
        client.setCity(city);
        client.setInitialOverdraft(initialOverdraft);
        try {
            BankCommander.myClientService.addClient(BankCommander.currentBank, client);
        } catch (ClientExcistsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {
        DataOutputStream outData = new DataOutputStream(out);
        Client client = null;

            switch (clientCommandArg[2]) {
                case "m":
                    client = new Client(Gender.MALE);
                    break;
                case "f":
                    client = new Client(Gender.FEMALE);
                    break;
                default:
                    System.out.println("Некорректно задан пол клиента");
            }
        try {
            client.setName(clientCommandArg[1]);
            client.setCity(clientCommandArg[3]);
            client.setEmail(clientCommandArg[4]);
            client.setPhone(clientCommandArg[5]);
            client.setInitialOverdraft(Float.valueOf(clientCommandArg[6]));
            BankCommander.myClientService.addClient(BankCommander.currentBank, client);
            try {
                outData.writeUTF("Client added" + clientCommandArg[1].toString());
            } catch (IOException e1) {
                try {
                    outData.writeUTF(e1.getMessage());
                } catch (IOException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        } catch (ClientExcistsException e) {
            System.out.println(e.getMessage());
            try {
                outData.writeUTF(e.getMessage());
            } catch (IOException e1) {
                e1.getMessage();
            }



        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Add client command");
    }
}
