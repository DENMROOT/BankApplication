package com.luxoft.bankapp.service.DAO;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.service.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class ClientDAOImpl extends  BaseDAOImpl implements ClientDAO {

    private static ClientDAOImpl instance;
    Logger clientDAOLog = Logger.getLogger("ClientDAOImpl");

    private ClientDAOImpl() {
    }

    public static ClientDAOImpl getInstance() {
        if (instance == null) {
            instance = new ClientDAOImpl();
        }
        return instance;
    }

    @Override
    public synchronized Client findClientByName(Bank bank, String name) throws ClientNotFoundException {
        Connection myConnection = openConnection();
        Client myClient = null;
            try {
                // 1) Create Preparedstatement
                PreparedStatement prepStatement = myConnection.prepareStatement("SELECT \n" +
                        "    C.ID AS ID,\n" +
                        "    C.NAME AS NAME,\n" +
                        "    C.GENDER AS GENDER,\n" +
                        "    C.EMAIL AS EMAIL,\n" +
                        "    C.PHONE AS PHONE,\n" +
                        "    C.CITY AS CITY,\n" +
                        "    C.ACTIVE_ACCOUNT_ID AS ACTIVEACCOUNTID\n" +
                        "FROM\n" +
                        "    CLIENTS C\n" +
                        "WHERE \n" +
                        "    BANK_ID = ?\n" +
                        "AND \n" +
                        "    NAME = ?");

                // 2) Set PreparedStatement param

                prepStatement.setLong(1, bank.getBankID());
                prepStatement.setString(2, name);

                // 3) Execute query and get the ResultSet
                ResultSet rs = prepStatement.executeQuery();

                // Iterate over results and print it
                if(rs.next()) {
                    // Retrieve by column name
                    long clientID = rs.getLong("ID");
                    String clientName = rs.getString("NAME");
                    String clientGender = rs.getString("GENDER");
                    String clientEmail = rs.getString("EMAIL");
                    String clientPhone = rs.getString("PHONE");
                    String clientCity = rs.getString("CITY");
                    long activeAccountID = rs.getLong("ACTIVEACCOUNTID");

                    // Display data
                    //System.out.print("ID: " + clientID + ", ");
                    //System.out.print("NAME: " + clientName + "\n");
                    switch (clientGender){
                        case "m" : myClient = new Client(Gender.MALE); break;
                        case "f" : myClient = new Client(Gender.FEMALE); break;
                        default :
                            System.out.println("Пол клиента задан неверно");
                    }

                    myClient.setClientID(clientID);
                    myClient.setName(clientName);
                    myClient.setEmail(clientEmail);
                    myClient.setPhone(clientPhone);
                    myClient.setCity(clientCity);
                    //myClient.setActiveAccount(activeAccountID);

                    AccountDAOImpl accountDAO = DaoFactory.getAccountDAO();
                    List <Account> accountsList = accountDAO.getClientAccounts(myClient.getClientID());

                    for (Account accountIterator : accountsList){
                        myClient.addAccount(accountIterator);
                        myClient.setActiveAccount(accountIterator);
                    }

                    clientDAOLog.fine("Найден клиент: " + myClient.getName());
                } else {
                    closeConnection();
                    throw new ClientNotFoundException("Клиент с указанным именем не найден");
                }
                closeConnection();
                return myClient;

            } catch(SQLException e) {
                System.out.println(e.getMessage());
                clientDAOLog.severe("SQLException" + e.getMessage());
            }
            return null;
    }

    @Override
    public synchronized Client findClientById(Bank bank, long clientId) throws ClientNotFoundException {
        Connection myConnection = openConnection();
        Client myClient = null;
        try {
            // 1) Create Preparedstatement
            PreparedStatement prepStatement = myConnection.prepareStatement("SELECT \n" +
                    "    C.ID AS ID,\n" +
                    "    C.NAME AS NAME,\n" +
                    "    C.GENDER AS GENDER,\n" +
                    "    C.EMAIL AS EMAIL,\n" +
                    "    C.PHONE AS PHONE,\n" +
                    "    C.CITY AS CITY,\n" +
                    "    C.ACTIVE_ACCOUNT_ID AS ACTIVEACCOUNTID\n" +
                    "FROM\n" +
                    "    CLIENTS C\n" +
                    "WHERE \n" +
                    "    BANK_ID = ?\n" +
                    "AND \n" +
                    "    ID = ?");

            // 2) Set PreparedStatement param

            prepStatement.setLong(1, bank.getBankID());
            prepStatement.setLong(2, clientId);

            // 3) Execute query and get the ResultSet
            ResultSet rs = prepStatement.executeQuery();

            // Iterate over results and print it
            if (rs.next()) {
                // Retrieve by column name
                long clientID = rs.getLong("ID");
                String clientName = rs.getString("NAME");
                String clientGender = rs.getString("GENDER");
                String clientEmail = rs.getString("EMAIL");
                String clientPhone = rs.getString("PHONE");
                String clientCity = rs.getString("CITY");
                long activeAccountID = rs.getLong("ACTIVEACCOUNTID");

                switch (clientGender){
                    case "m" : myClient = new Client(Gender.MALE); break;
                    case "f" : myClient = new Client(Gender.FEMALE); break;
                    default :
                        System.out.println("Пол клиента задан неверно");
                }

                myClient.setClientID(clientID);
                myClient.setName(clientName);
                myClient.setEmail(clientEmail);
                myClient.setPhone(clientPhone);
                myClient.setCity(clientCity);
                //myClient.setActiveAccount(activeAccountID);

                AccountDAOImpl accountDAO = DaoFactory.getAccountDAO();
                List <Account> accountsList = accountDAO.getClientAccounts(myClient.getClientID());

                for (Account accountIterator : accountsList){
                    myClient.addAccount(accountIterator);
                    myClient.setActiveAccount(accountIterator);
                }
                clientDAOLog.fine("Найден клиент по ID: " + myClient.getName());
            } else {
                closeConnection();
                throw new ClientNotFoundException("Клиент с указанным именем не найден");
            }
            closeConnection();
            return myClient;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            clientDAOLog.severe("SQLException" + e.getMessage());
        }
        return null;
    }

    @Override
    public synchronized List<Client> getAllClients(Bank bank) {
        Connection myConnection = openConnection();
        Client myClient = null;
        List <Client> clientsList= new ArrayList<>();
        try {
            // 1) Create Preparedstatement
            PreparedStatement prepStatement = myConnection.prepareStatement("SELECT \n" +
                    "    C.ID AS ID,\n" +
                    "    C.NAME AS NAME,\n" +
                    "    C.GENDER AS GENDER,\n" +
                    "    C.EMAIL AS EMAIL,\n" +
                    "    C.PHONE AS PHONE,\n" +
                    "    C.CITY AS CITY,\n" +
                    "    C.ACTIVE_ACCOUNT_ID AS ACTIVEACCOUNTID\n" +
                    "FROM\n" +
                    "    CLIENTS C\n" +
                    "WHERE \n" +
                    "    BANK_ID = ?");

            // 2) Set PreparedStatement param

            prepStatement.setLong(1, bank.getBankID());

            // 3) Execute query and get the ResultSet
            ResultSet rs = prepStatement.executeQuery();

            // Iterate over results and print it
            while(rs.next()) {
                // Retrieve by column name
                long clientID = rs.getLong("ID");
                String clientName = rs.getString("NAME");
                String clientGender = rs.getString("GENDER");
                String clientEmail = rs.getString("EMAIL");
                String clientPhone = rs.getString("PHONE");
                String clientCity = rs.getString("CITY");
                long activeAccountID = rs.getLong("ACTIVEACCOUNTID");

                switch (clientGender){
                    case "m" : myClient = new Client(Gender.MALE); break;
                    case "f" : myClient = new Client(Gender.FEMALE); break;
                    default :
                        System.out.println("Пол клиента задан неверно");
                }

                myClient.setClientID(clientID);
                myClient.setName(clientName);
                myClient.setEmail(clientEmail);
                myClient.setPhone(clientPhone);
                myClient.setCity(clientCity);
                //myClient.setActiveAccount(activeAccountID);

                //Вытягиваем все счета клиента по его ID и записываем его в Entity client
                AccountDAOImpl accountDAO = DaoFactory.getAccountDAO();
                List <Account> accountsList = accountDAO.getClientAccounts(myClient.getClientID());

                for (Account accountIterator : accountsList){
                    myClient.addAccount(accountIterator);
                    myClient.setActiveAccount(accountIterator);
                }

                clientsList.add(myClient);
            }
            closeConnection();
            clientDAOLog.fine("Выгружены клиенты банка: " + bank.getName());
            return clientsList;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            clientDAOLog.severe("SQLException" + e.getMessage());
        }
        return null;
    }

    @Override
    public synchronized void save(Client client) {
        Connection myConnection = openConnection();



        try {
            // 1) Create PreparedStatement
            PreparedStatement prepStatement = myConnection.prepareStatement("UPDATE \n" +
                    "    CLIENTS\n" +
                    "SET \n" +
                    "    NAME=?,\n" +
                    "    GENDER=?,\n" +
                    "    EMAIL = ?,\n" +
                    "    PHONE = ?,\n" +
                    "    CITY = ?,\n" +
                    "    ACTIVE_ACCOUNT_ID = ?\n" +
                    "WHERE ID=?;");

            // 2) Set PreparedStatement param

            prepStatement.setString(1, client.getName());

            switch (client.getGender()) {
                case MALE:prepStatement.setString(2,"m"); break;
                case FEMALE:prepStatement.setString(2,"f"); break;
            }
            prepStatement.setString(3, client.getEmail());
            prepStatement.setString(4, client.getPhone());
            prepStatement.setString(5, client.getCity());
            prepStatement.setLong(6, client.getActiveAccount().getAccountId());
            prepStatement.setLong(7, client.getClientID());

            // 3) Execute query and get the ResultSet
            prepStatement.executeUpdate();

            int count = prepStatement.getUpdateCount();
            System.out.println("Количество затронутых записей клиентов:" + count);

            closeConnection();
            clientDAOLog.fine("Сохранен клиент: " + client.getName());

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            clientDAOLog.severe("SQLException" + e.getMessage());
        }

    }

    @Override
    public synchronized void insert(Bank bank, Client client) throws SQLException, DAOException {
        final String clientSQL = "INSERT INTO CLIENTS (BANK_ID,NAME,GENDER,EMAIL,PHONE,CITY,ACTIVE_ACCOUNT_ID) VALUES (?,?,?,?,?,?,?)";
        try (
                Connection myConnection = openConnection();
                final PreparedStatement clientStmt = myConnection.prepareStatement(clientSQL);
        ) {
            clientStmt.setLong(1, bank.getBankID());
            clientStmt.setString(2, client.getName());
            switch (client.getGender()) {
                case MALE:clientStmt.setString(3,"m"); break;
                case FEMALE:clientStmt.setString(3,"f"); break;
            }
            clientStmt.setString(4, client.getEmail());
            clientStmt.setString(5, client.getPhone());
            clientStmt.setString(6, client.getCity());
            clientStmt.setNull(7, 0);

            if (clientStmt.executeUpdate() == 0) {
                System.out.println("Ошибка вставки клиента");
                throw new DAOException("Impossible to save Client in DB. Transaction is rolled back.");
            }
            ResultSet rs = clientStmt.getGeneratedKeys();
            if (rs == null || !rs.next()) {
                System.out.println("Ошибка получения нового ID");
                throw new DAOException("Impossible to save in DB. Can't get clientID.");
            }
            int count = clientStmt.getUpdateCount();
            System.out.println("Затронуто записей клиентов:" + count);
            Integer clientId = rs.getInt(1);
            client.setClientID(clientId);
            clientDAOLog.fine("добавлен клиент: " + client.getName());

        } catch (DAOException | SQLException e) {
            System.out.println(e.getMessage());
            clientDAOLog.severe("Exception" + e.getMessage());
        }

    }


    @Override
    public synchronized void remove(Client client) {

        AccountDAOImpl accountDao = DaoFactory.getAccountDAO();
        accountDao.removeByClientId(client.getClientID());
        Connection myConnection = openConnection();

        try {
            // 1) Create PreparedStatement
            PreparedStatement prepStatement = myConnection.prepareStatement("DELETE \n" +
                    "FROM \n" +
                    "    CLIENTS\n" +
                    "WHERE \n" +
                    "    ID=?");

            // 2) Set PreparedStatement param

            prepStatement.setLong(1, client.getClientID());

            // 3) Execute query and get the ResultSet
            prepStatement.executeUpdate();

            int count = prepStatement.getUpdateCount();
            System.out.println("Количество затронутых записей клиентов:" + count);

            closeConnection();
            clientDAOLog.fine("Удален клиент: " + client.getName());

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            clientDAOLog.severe("SQLException" + e.getMessage());
        }
    }
}
