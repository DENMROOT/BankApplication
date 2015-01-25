package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Денис on 25.01.2015.
 */
public class BankReportContainer implements Serializable{
    private int numberOfClients;

    private int accountsNumber;

    private Set<Client> clientsSorted;

    private float bankCreditSum;

    private Map<String, List<Client>> clientsByCity;

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getAccountsNumber() {
        return accountsNumber;
    }

    public void setAccountsNumber(int accountsNumber) {
        this.accountsNumber = accountsNumber;
    }


    public Set<Client> getClientsSorted() {
        return clientsSorted;
    }

    public void setClientsSorted(Set<Client> clientsSorted) {
        this.clientsSorted = clientsSorted;
    }

    public float getBankCreditSum() {
        return bankCreditSum;
    }

    public void setBankCreditSum(float bankCreditSum) {
        this.bankCreditSum = bankCreditSum;
    }

    public Map<String, List<Client>> getClientsByCity() {
        return clientsByCity;
    }

    public void setClientsByCity(Map<String, List<Client>> clientsByCity) {
        this.clientsByCity = clientsByCity;
    }
}
