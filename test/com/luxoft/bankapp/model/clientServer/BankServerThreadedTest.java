package com.luxoft.bankapp.model.clientServer;

import com.luxoft.bankapp.service.clientServerMultithreading.BankClientMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Makarov Denis on 06.02.2015.
 */
public class BankServerThreadedTest {

    @Test
    public void testBankServerMockClientWithdraw () throws InterruptedException {
        ExecutorService clientsPool = Executors.newFixedThreadPool(100);

        List<Future<Long>> clientList = new ArrayList<>();
        long avgDate = 0;
        int resultCounter = 0;
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            Future future = clientsPool.submit(new BankClientMock());
            clientList.add(future);
        }

        for(Future<Long> result: clientList){
            try {
                if (result.isDone()) {
                    resultCounter++;
                    float temp = result.get();
                    avgDate += temp;
                    System.out.println("Поток: " + resultCounter + " Time: " + temp);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Среднее время выполнения для " + resultCounter + " потоков: " +avgDate/resultCounter);
    }
}
