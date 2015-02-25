package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {

    private static AccountDAOImpl instance;
    public final static Logger accountDAOLog = Logger.getLogger(AccountDAOImpl.class.getName());

    private AccountDAOImpl() {
    }

    public static AccountDAOImpl getInstance() {
        if (instance == null) {
            instance = new AccountDAOImpl();
        }
        return instance;
    }

    @Override
    public synchronized void save(Account account) {
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
                accountDAOLog.fine("Количество сохраненных записей счетов:" + count);

                closeConnection();

            } catch(SQLException e) {
                System.out.println(e.getMessage());
                accountDAOLog.severe("SQLException" + e.getMessage());
            }
    }

    @Override
    public synchronized void insert(Client client, Account account) {
        final String clientSQL = "INSERT INTO ACCOUNTS (CLIENT_ID,ACCOUNT_TYPE,INITIALOVERDRAFT,BALANCE,OVERDRAFT) VALUES (?,?,?,?,?)";
        try (   Connection myConnection = openConnection();
                final PreparedStatement accountStmt = myConnection.prepareStatement(clientSQL);
        ) {
            accountStmt.setLong(1, client.getClientID());
            switch (account.getAccountType()) {
                case "S":accountStmt.setString(2,"S"); break;
                case "C":accountStmt.setString(2,"C"); break;
            }
            accountStmt.setFloat(3, account.getInitialOverdraft());
            accountStmt.setFloat(4, account.getBalance());
            accountStmt.setFloat(5, account.getOverdraft());

            if (accountStmt.executeUpdate() == 0) {
                System.out.println("Ошибка вставки клиента");
                throw new DAOException("Impossible to save Client in DB. Transaction is rolled back.");
            }
            ResultSet rs = accountStmt.getGeneratedKeys();
            if (rs == null || !rs.next()) {
                System.out.println("Ошибка получения нового ID");
                throw new DAOException("Impossible to save in DB. Can't get clientID.");
            }
            int count = accountStmt.getUpdateCount();
            System.out.println("Затронуто записей счетов:" + count);
            accountDAOLog.fine("Количество вставленных записей счетов:" + count);
            Integer accountId = rs.getInt(1);
            account.setAccountId(accountId);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
            accountDAOLog.severe("DAOException" + e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            accountDAOLog.severe("SQLException" + e.getMessage());
        }
    }

    @Override
    public synchronized void removeByClientId(long id) {
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
            accountDAOLog.fine("Количество удаленных записей счетов:" + count);

            closeConnection();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            accountDAOLog.severe("SQLException" + e.getMessage());
        }
    }

    @Override
    public synchronized List<Account> getClientAccounts(long id) {
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
                accountDAOLog.fine("Запрошены счета клиента ID: " + id);
                return accountsList;

            } catch(SQLException e) {
                System.out.println(e.getMessage());
                accountDAOLog.severe("SQLException" + e.getMessage());
            }
            return null;
    }

    @Override
    public synchronized float getClientAccountBalance (long accountID) {
        Connection myConnection = openConnection();
        Client myClient = null;
        Account myAccount = null;
        List <Account> accountsList = new ArrayList<>();
        float accountBalance = 0;
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
                    "WHERE A.ID = ?");

            // 2) Set PreparedStatement param

            prepStatement.setLong(1, accountID);

            // 3) Execute query and get the ResultSet
            ResultSet rs = prepStatement.executeQuery();

            // Iterate over results and print it
            while(rs.next()) {
                // Retrieve by column name
                long accID = rs.getLong("ID");
                String accountType = rs.getString("ACCOUNTTYPE");
                accountBalance = rs.getFloat("BALANCE");
                float accountOverdraft = rs.getFloat("OVERDRAFT");

                switch (accountType){
                    case "S" : myAccount = new SavingAccount(accID, accountBalance); break;
                    case "C" : myAccount = new CheckingAccount(accID, accountBalance, accountOverdraft); break;
                    default :
                        System.out.println("Тип счета задан неверно");
                }
            }
            closeConnection();
            accountDAOLog.fine("Запрошен баланс по счету ID: " + accountID);
            return accountBalance;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            accountDAOLog.severe("SQLException" + e.getMessage());
        }
        return 0;
    }

    @Override
    public synchronized void transferFunds(Account accountFrom, Account accountTo, float amount) {
        Connection myConnection = openConnection();

        try {
            myConnection.setAutoCommit(false);
            // 1) Create Preparedstatement
            PreparedStatement prepStatementFrom = myConnection.prepareStatement("UPDATE \n" +
                    "    ACCOUNTS \n" +
                    "SET \n" +
                    "    BALANCE=?,\n" +
                    "    OVERDRAFT=?\n" +
                    "WHERE ID=?;\n");

            PreparedStatement prepStatementTo = myConnection.prepareStatement("UPDATE \n" +
                    "    ACCOUNTS \n" +
                    "SET \n" +
                    "    BALANCE=?,\n" +
                    "    OVERDRAFT=?\n" +
                    "WHERE ID=?;\n");

            // 2) Set PreparedStatement param

            prepStatementFrom.setFloat(1, accountFrom.getBalance());
            prepStatementFrom.setFloat(2, accountFrom.getOverdraft());
            prepStatementFrom.setLong(3, accountFrom.getAccountId());

            prepStatementTo.setFloat(1, accountTo.getBalance());
            prepStatementTo.setFloat(2, accountTo.getOverdraft());
            prepStatementTo.setLong(3, accountTo.getAccountId());

            // 3) Execute query and get the ResultSet
            prepStatementFrom.executeUpdate();
            prepStatementTo.executeUpdate();

            myConnection.commit();

            int count = prepStatementFrom.getUpdateCount() + prepStatementTo.getUpdateCount();
            System.out.println("Количество затронутых записей счетов:" + count);
            accountDAOLog.info("Перевод средств, количество затронутых записей счетов: " + count);

        } catch(SQLException e) {
            try {
                myConnection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getErrorCode());
                accountDAOLog.severe("SQLException" + e1.getMessage());
            }
            System.out.println(e.getErrorCode());
            accountDAOLog.severe("SQLException" + e.getErrorCode());
        } finally {
            try {
                myConnection.setAutoCommit(true);
                closeConnection();
            } catch (SQLException e) {
                System.out.println(e.getErrorCode());
                accountDAOLog.severe("SQLException" + e.getErrorCode());
            }
        }
    }

}
