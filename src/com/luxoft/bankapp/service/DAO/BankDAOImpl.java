package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.BankInfo;
import com.luxoft.bankapp.model.Client;

import java.sql.*;
import java.util.*;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class BankDAOImpl extends BaseDAOImpl implements BankDAO {

    private static BankDAOImpl instance;

    private BankDAOImpl() {
    }

    public static BankDAOImpl getInstance() {
        if (instance == null) {
            instance = new BankDAOImpl();
        }
        return instance;
    }

    @Override
    public Bank getBankByName(String name) {
        Connection myConnection = openConnection();
        Bank myBank = new Bank();
        try {
            PreparedStatement stmtBank = myConnection.prepareStatement("SELECT B.ID AS ID, B.NAME AS NAME FROM BANK B WHERE NAME = ?;");

            stmtBank.setString(1, name);

            ResultSet rs = stmtBank.executeQuery();

            // Iterate over results and print it
            while(rs.next()) {
                // Retrieve by column name
                long bankID = rs.getLong("ID");
                String bankName = rs.getString("NAME");

                myBank.setBankID(bankID);
                myBank.setName(bankName);
            }
            closeConnection();
            return myBank;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public BankInfo getBankInfo(Bank bank) {
        BankInfo myBankInfo = new BankInfo();
        Connection myConnection = openConnection();
        Map<String, List<Client>> clientsByCity = new TreeMap<>();
        List <Client> clientsList = new ArrayList<>();

        try {
            // 1) Create statement
            PreparedStatement stmtCou = myConnection.prepareStatement("SELECT COUNT(ID) AS COUNT FROM CLIENTS WHERE BANK_ID = ?;");

            stmtCou.setLong(1, bank.getBankID());

            ResultSet rs = stmtCou.executeQuery();

            // Iterate over results and print it
            while(rs.next()) {
                // Retrieve by column name
                int count = rs.getInt("COUNT");
                myBankInfo.setNumberOfClients(count);
            }
            // 1) Create statement
            PreparedStatement stmtSum = myConnection.prepareStatement("SELECT SUM(BALANCE) AS SUM FROM \n" +
                    "ACCOUNTS A\n" +
                    "JOIN CLIENTS C\n" +
                    "ON A.CLIENT_ID  = C.ID \n" +
                    "WHERE C.BANK_ID = ?;");

            stmtSum.setLong(1, bank.getBankID());

            ResultSet rsAcc = stmtSum.executeQuery();

            // Iterate over results and print it
            while(rsAcc.next()) {
                // Retrieve by column name
                float sum = rsAcc.getFloat("SUM");
                myBankInfo.setTotalAccountSum(sum);
            }

            // Ищем клиентов по городам
            ClientDAOImpl clientDao = new ClientDAOImpl();
            clientsList = clientDao.getAllClients(bank);

            for(Client client: clientsList){
                if(clientsByCity.containsKey(client.getCity()))
                    clientsByCity.get(client.getCity()).add(client);
                else{
                    List clients = new ArrayList<Client>();
                    clients.add(client);
                    clientsByCity.put(client.getCity(), clients);
                }
            }

            myBankInfo.setClientsByCity(clientsByCity);
            // Display data
            //System.out.print("ID: " + bankID + ", ");
            //System.out.print("NAME: " + bankName + "\n");

            closeConnection();
        } catch(SQLException e) {
            e.getMessage();
        }
        return myBankInfo;
    }
}
