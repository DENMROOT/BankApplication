package com.luxoft.bankapp.model.clientServer;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DAO.*;
import com.luxoft.bankapp.service.clientServerMultithreading.BankClientMock;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Makarov Denis on 06.02.2015.
 */
public class BankServerThreadedTest {

    @Test
    public void testBankServerMockClientWithdraw () throws InterruptedException, ClientNotFoundException {
        ExecutorService clientsPool = Executors.newFixedThreadPool(100);
        AccountDAO accountDAO = DaoFactory.getAccountDAO();
        float initialBalance = accountDAO.getClientAccountBalance(2);

        List<Future<Long>> clientList = new ArrayList<>();
        long avgDate = 0;
        int resultCounter = 0;
        for (int i = 0; i < 1000; i++) {
            clientList.add(clientsPool.submit(new BankClientMock()));
            //Thread.sleep(10);
        }

        for(Future<Long> result: clientList){
            try {
                //if (result.isDone()) {
                    float temp = result.get();

                resultCounter++;
                    avgDate += temp;
                    System.out.println("Поток: " + resultCounter + " Time: " + temp);
                //}
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        float finalBalance = accountDAO.getClientAccountBalance(2);

        clientsPool.shutdown();
        try {
            clientsPool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientsPool.shutdownNow();

        System.out.println("Среднее время выполнения для " + resultCounter + " потоков: " + ((resultCounter == 0)?0:avgDate/resultCounter));
        assertEquals(initialBalance - finalBalance, 1000, 0.0f);
    }
}
