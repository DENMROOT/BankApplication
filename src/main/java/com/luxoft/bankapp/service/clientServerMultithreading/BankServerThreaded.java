package com.luxoft.bankapp.service.clientServerMultithreading;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 06.02.2015.
 */
public class BankServerThreaded {
    public static final int POOL_SIZE = 10;
    public static final int PORT = 4444 ;
    public static AtomicInteger clientsCounter = new AtomicInteger(0);

    public final static Logger bankServerThreadedLog = Logger.getLogger(BankServerThreaded.class.getName());

    public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
    ServerSocket serverSocket = null;

    /* 2 Вариант подключения настроек логирования, без аргументов JVM
    try {
        LogManager.getLogManager().readConfiguration(
                new FileInputStream(".\\log\\logging.properties"));
    } catch (IOException e) {
        System.err.println("Could not setup logger configuration: " + e.toString());
        System.exit(0);
    }
    */
        BankServerMonitor bankServerMonitor = new BankServerMonitor();
        Thread monitor = new Thread(bankServerMonitor);
        monitor.setDaemon(true);
        monitor.start();

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Ошибка инициализации сервера" + e.getMessage());
            bankServerThreadedLog.severe("Ошибка инициализации сервера" + e.getMessage());
        }

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ServerThread(clientSocket));
                clientsCounter.incrementAndGet();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            bankServerThreadedLog.severe("Exception: " + e.getMessage());
        }
    }

}
