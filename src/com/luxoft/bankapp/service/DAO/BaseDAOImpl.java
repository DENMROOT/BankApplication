package com.luxoft.bankapp.service.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class BaseDAOImpl implements BaseDAO {
    Connection myConnection;

    @Override
    public Connection openConnection() {
        final String DB_NAME="BankApplication";
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
            myConnection = DriverManager.getConnection("jdbc:h2:E:\\PROGRAMMING\\IntellijIDEA\\Projects\\BankApplication\\db\\" + DB_NAME,
                    "sa", // login
                    "" // password
            );
            return myConnection;
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            myConnection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
