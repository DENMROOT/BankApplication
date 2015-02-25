package com.luxoft.bankapp.service.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by Makarov Denis on 27.01.2015.
 */
public class BaseDAOImpl implements BaseDAO {
    Connection myConnection;
    public final static Logger baseDAOLog = Logger.getLogger(BaseDAOImpl.class.getName());

    @Override
    public Connection openConnection() {
        final String DB_NAME="BankApplication";
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
            myConnection = DriverManager.getConnection("jdbc:h2:E:\\Denis Makarov\\IntellijIDEA\\Projects\\BankApplication\\db\\" + DB_NAME,
                    "sa", // login
                    "" // password
            );
            return myConnection;
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            baseDAOLog.severe("Exception" + e.getMessage());
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            myConnection.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            baseDAOLog.severe("SQLException" + e.getMessage());
        }
    }
}
