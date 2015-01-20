package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.*;

/**
 * Created by Makarov Denis on 19.01.2015.
 */
public class BankReport {
    public int getNumberOfClients(Bank bank) {
        BankServiceImpl myBankService = new BankServiceImpl();
        return myBankService.getClients(bank).size();
    }

    public int getAccountsNumber(Bank bank) {
        BankServiceImpl myBankService = new BankServiceImpl();
        return myBankService.getAllAccounts(bank).size();
    }

    public Set<Client> getClientsSorted(Bank bank) {
        BankServiceImpl myBankService = new BankServiceImpl();
        Set<Client> sortedBankClients = new TreeSet<Client> (myBankService.getClients(bank));
        return sortedBankClients;
    }

    public float getBankCreditSum(Bank bank) {
        float totalBankOverdraft=0.0f;
        BankServiceImpl myBankService = new BankServiceImpl();
        Set<Account> allBankAccounts = new HashSet<Account> (myBankService.getAllAccounts(bank));
        for (Iterator<Account> accIterator = allBankAccounts.iterator(); accIterator.hasNext(); ) {
            Account account = accIterator.next();
            totalBankOverdraft += account.getTotalAccountOverdraft();
        }
        return totalBankOverdraft;
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        BankServiceImpl myBankService = new BankServiceImpl();

        Map<String, List<Client>> resultMap = new TreeMap<String, List<Client>>();
        for(Client client:myBankService.getClients(bank)){
            if(resultMap.containsKey(client.getCity()))
                resultMap.get(client.getCity()).add(client);
            else{
                List clients = new ArrayList<Client>();
                clients.add(client);
                resultMap.put(client.getCity(), clients);
            }
        }
        for(String city:resultMap.keySet()){
            List<Client> clientsByCity = resultMap.get(city);
            for(Client client:clientsByCity){
                System.out.println(city+"::"+client);
            }
        }
        return resultMap;
    }
}
