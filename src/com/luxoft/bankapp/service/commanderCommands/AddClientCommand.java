package com.luxoft.bankapp.service.commanderCommands;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankCommander;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.Command;
import com.luxoft.bankapp.service.clientServer.BankServer;

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

        switch (gender) {
            case "m":
                client = new Client(Gender.MALE);
                client.setName(clientName);
                try {
                    BankCommander.myBankService.addClient(BankCommander.currentBank, client);
                } catch (ClientExcistsException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "f":
                client = new Client(Gender.FEMALE);
                client.setName(clientName);
                try {
                    BankCommander.myBankService.addClient(BankCommander.currentBank, client);
                } catch (ClientExcistsException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                System.out.println("Некорректно задан пол клиента");
        }
    }

    @Override
    public void execute_server(OutputStream out, Socket server, Bank bank, String[] clientCommandArg) {
        DataOutputStream outData = new DataOutputStream(out);
        Client client = null;

            switch (clientCommandArg[2]) {
                case "m":
                    client = new Client(Gender.MALE);
                    try {
                        client.setName(clientCommandArg[1]);
                    } catch (IllegalArgumentException e){
                        try {
                            outData.writeUTF(e.getMessage());
                        } catch (IOException e1) {
                            e1.getMessage();
                        }
                    }
                    try {
                        BankCommander.myBankService.addClient(BankCommander.currentBank, client);
                        client.setCity(clientCommandArg[2]);
                        client.setEmail(clientCommandArg[3]);
                        client.setPhone(clientCommandArg[4]);
                        outData.writeUTF("Client added" + clientCommandArg[1].toString());
                    } catch (ClientExcistsException e) {
                        System.out.println(e.getMessage());
                        try {
                            outData.writeUTF(e.getMessage());
                        } catch (IOException e1) {
                            e1.getMessage();
                        }
                    }catch (IOException ee) {
                        System.out.println(ee.getMessage());
                        try {
                            outData.writeUTF(ee.getMessage());
                        } catch (IOException e1) {
                            e1.getMessage();
                        }
                    }
                    break;
                case "f":
                    client = new Client(Gender.FEMALE);
                    client.setName(clientCommandArg[1]);
                    try {
                        BankCommander.myBankService.addClient(BankCommander.currentBank, client);
                        client.setCity(clientCommandArg[2]);
                        client.setEmail(clientCommandArg[3]);
                        client.setPhone(clientCommandArg[4]);
                        outData.writeUTF("Client added" + clientCommandArg[1].toString());
                    } catch (ClientExcistsException e) {
                        System.out.println(e.getMessage());
                        try {
                            outData.writeUTF(e.getMessage());
                        } catch (IOException e1) {
                            e1.getMessage();
                        }
                    }catch (IOException ee) {
                        System.out.println(ee.getMessage());
                        try {
                            outData.writeUTF(ee.getMessage());
                        } catch (IOException e1) {
                            e1.getMessage();
                        }
                    }
                    break;
                default:
                    System.out.println("Некорректно задан пол клиента");
            }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Add client command");
    }
}
