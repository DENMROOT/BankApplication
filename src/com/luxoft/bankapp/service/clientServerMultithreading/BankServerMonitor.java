package com.luxoft.bankapp.service.clientServerMultithreading;

/**
 * Created by Makarov Denis on 06.02.2015.
 */
public class BankServerMonitor implements Runnable{
    @Override
    public void run() {
        while (true) {
            System.out.println("Количество клиентов в очереди: " + BankServerThreaded.getClientsCounter());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
