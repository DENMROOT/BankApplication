package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.Bank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class BankDAOImpl extends  BaseDAOImpl implements BankDAO {


    @Override
    public Bank getBankByName(String name) {
        Connection myConnection = openConnection();
        Bank myBank = new Bank();
        try {
            // 1) Create statement
            Statement stmt = myConnection.createStatement();
            String sql = "SELECT" +
                    " B.ID AS ID," +
                    " B.NAME AS NAME" +
                    " FROM" +
                    " BANK B;";
            // 2) Execute query and get the ResultSet
            ResultSet rs = stmt.executeQuery(sql);

            // Iterate over results and print it
            while(rs.next()) {
                // Retrieve by column name
                long bankID = rs.getLong("ID");
                String bankName = rs.getString("NAME");

                // Display data
                //System.out.print("ID: " + bankID + ", ");
                //System.out.print("NAME: " + bankName + "\n");
                myBank.setBankID(bankID);
                myBank.setName(bankName);
            }
            closeConnection();
            return myBank;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
