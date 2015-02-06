package com.luxoft.bankapp.service.clientServerMultithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Makarov Denis on 06.02.2015.
 */
public class BankServerThreaded {
    public static final int POOL_SIZE = 2;
    public static final int PORT = 4444 ;
    private static AtomicInteger clientsCounter = new AtomicInteger(0);

    public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
    ServerSocket serverSocket = null;
        BankServerMonitor bankServerMonitor = new BankServerMonitor();
        Thread monitor = new Thread(bankServerMonitor);
        monitor.setDaemon(true);
        monitor.start();

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Ошибка инициализации сервера" + e.getMessage());
        }

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ServerThread(clientSocket));
                clientsCounter.incrementAndGet();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getClientsCounter() {
        return clientsCounter.get();
    }

    public static void setClientsCounter(int counter) {
        clientsCounter.set(counter);
    }
}
