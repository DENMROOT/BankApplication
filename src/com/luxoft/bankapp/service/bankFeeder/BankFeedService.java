package com.luxoft.bankapp.service.bankFeeder;

import com.luxoft.bankapp.model.Bank;

import java.io.*;
import java.util.*;

/**
 * Created by Makarov Denis on 19.01.2015.
 */
public class BankFeedService implements IBankFeedService{
    Bank activeBank;

    public BankFeedService(Bank bank){
        this.activeBank = bank;
    };

    public void parseFeed(String feed){
        Map<String, String> result = new HashMap<>();
        String[] expressions = feed.split(";");
        for(String expression : expressions){
            String leftPart = expression.split("=")[0];
            String rightPart = expression.split("=")[1];
            result.put(leftPart, rightPart);
        }
        //System.out.println(result.toString());
        activeBank.parseFeed(result);
    }

    public void loadFeeds(String folder){
        File dir = new File(folder);
        if(dir.exists()){
            File[] files = dir.listFiles();
            for(File file : files){
                try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while((line = reader.readLine()) != null){
                        parseFeed(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
