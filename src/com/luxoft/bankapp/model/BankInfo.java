package com.luxoft.bankapp.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Makarov Denis on 28.01.2015.
 */
public class BankInfo {
    private int numberOfClients;
    private double totalAccountSum;
    private Map<String, List<Client>> clientsByCity;

    /**
     * Total number of clients of the bank
     */
    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    /**
     * The sum of all accounts of all clients
     */
    public double getTotalAccountSum() {
        return totalAccountSum;
    }

    public void setTotalAccountSum(double totalAccountSum) {
        this.totalAccountSum = totalAccountSum;
    }

    /**
     * List of clients by the city
     */
    public Map<String, List<Client>> getClientsByCity() {
        return clientsByCity;
    }

    public void setClientsByCity(Map<String, List<Client>> clientsByCity) {
        this.clientsByCity = clientsByCity;
    }
}
