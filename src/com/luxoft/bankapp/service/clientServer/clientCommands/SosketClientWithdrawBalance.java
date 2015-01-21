package com.luxoft.bankapp.service.clientServer.clientCommands;

import com.luxoft.bankapp.service.clientServer.clientCommands.ClientCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Makarov Denis on 21.01.2015.
 */
public class SosketClientWithdrawBalance implements ClientCommand {
    @Override
    public void execute(DataOutputStream out, Socket client) {
        try {
            System.out.println("Введите сумму списания");

            Scanner paramScan = new Scanner(System.in);
            String withdrawalSum=paramScan.nextLine(); // initialize client name

            out.writeUTF("BankClient Withdrawal command" + "&" + withdrawalSum);
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
        System.out.println("Withdraw funds from balance");
    }
}
