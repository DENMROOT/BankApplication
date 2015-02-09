package com.luxoft.bankapp.service.clientServerMultithreading;

import com.luxoft.bankapp.service.clientServer.clientCommands.ClientCommand;
import com.luxoft.bankapp.service.clientServer.clientCommands.SocketClientGetClientBalance;
import com.luxoft.bankapp.service.clientServer.clientCommands.SosketClientWithdrawBalance;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by Makarov Denis on 06.02.2015.
 */
public class BankClientMock implements Callable {

    @Override
    public Object call() throws Exception {
        String serverName = "localhost";
        int port = 4444;
        try
        {
            long start = new Date().getTime();
            Socket client = new Socket(serverName, port);
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out =
                    new DataOutputStream(outToServer);

            out.writeUTF("BankClient Get balance command" + "&" + "Денис Макаров");
            InputStream inFromServer = null;
            inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            in.readUTF();
            out.writeUTF("BankClient Withdrawal command" + "&" + "1");
            in.readUTF();
            out.writeUTF("EXIT" + "&" + "null");
            in.readUTF();
            long time = new Date().getTime()-start;
            in.close();
            out.close();
            client.close();
            return time;

        }catch(IOException e)
        {
            System.out.println("Соединение с сервером не установлено: " + e.getMessage());
            return 0;
        }
    }
}


