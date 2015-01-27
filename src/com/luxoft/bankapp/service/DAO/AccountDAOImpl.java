package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
    @Override
    public void save(Account account) {
        Connection myConnection = openConnection();

        try {
            // 1) Create Preparedstatement
            PreparedStatement prepStatement = myConnection.prepareStatement("UPDATE \n" +
                    "    ACCOUNTS \n" +
                    "SET \n" +
                    "    BALANCE=?,\n" +
                    "    OVERDRAFT=?\n" +
                    "WHERE ID=?;\n");

            // 2) Set PreparedStatement param

            prepStatement.setFloat(1, account.getBalance());
            prepStatement.setFloat(2, account.getOverdraft());
            prepStatement.setLong(3, account.getAccountId());
            // 3) Execute query and get the ResultSet
            prepStatement.executeUpdate();

            int count = prepStatement.getUpdateCount();
            System.out.println("Количество затронутых записей счетов:" + count);

            closeConnection();

        } catch(SQLException e) {
            e.getMessage();
        }


    }

    @Override
    public void removeByClientId(long id) {
        Connection myConnection = openConnection();

        try {
            // 1) Create Preparedstatement
            PreparedStatement prepStatement = myConnection.prepareStatement("DELETE \n" +
                    "FROM \n" +
                    "    ACCOUNTS \n" +
                    "WHERE \n" +
                    "    CLIENT_ID=?");

            // 2) Set PreparedStatement param

            prepStatement.setLong(1, id);
            // 3) Execute query and get the ResultSet
            prepStatement.executeUpdate();

            int count = prepStatement.getUpdateCount();
            System.out.println("Количество затронутых записей счетов:" + count);

            closeConnection();

        } catch(SQLException e) {
            e.getMessage();
        }
    }

    @Override
    public List<Account> getClientAccounts(long id) {
        Connection myConnection = openConnection();
        Client myClient = null;
        Account myAccount = null;
        List <Account> accountsList = new ArrayList<>();

        try {
            // 1) Create Preparedstatement
            PreparedStatement prepStatement = myConnection.prepareStatement("SELECT \n" +
                    "    A.ID AS ID,\n" +
                    "    A.CLIENT_ID AS CLIENTID,\n" +
                    "    A.ACCOUNT_TYPE AS ACCOUNTTYPE,\n" +
                    "    A.INITIALOVERDRAFT AS INITIALOVERDRAFT,\n" +
                    "    A.BALANCE AS BALANCE,\n" +
                    "    A.OVERDRAFT AS OVERDRAFT\n" +
                    "FROM ACCOUNTS A\n" +
                    "WHERE A.CLIENT_ID = ?");

            // 2) Set PreparedStatement param

            prepStatement.setLong(1, id);

            // 3) Execute query and get the ResultSet
            ResultSet rs = prepStatement.executeQuery();

            // Iterate over results and print it
            while(rs.next()) {
                // Retrieve by column name
                long accountID = rs.getLong("ID");
                long clientID = rs.getLong("CLIENTID");
                String accountType = rs.getString("ACCOUNTTYPE");
                float initialOverdraft = rs.getFloat("INITIALOVERDRAFT");
                float accountBalance = rs.getFloat("BALANCE");
                float accountOverdraft = rs.getFloat("OVERDRAFT");

                switch (accountType){
                    case "S" : myAccount = new SavingAccount(accountID, accountBalance); break;
                    case "C" : myAccount = new CheckingAccount(accountID, accountBalance, accountOverdraft); break;
                    default :
                        System.out.println("Тип счета задан неверно");
                }

                accountsList.add(myAccount);
                //myClient.setActiveAccount(activeAccountID);
            }
            closeConnection();
            return accountsList;

        } catch(SQLException e) {
            e.getMessage();
        }
        return null;
    }

}
