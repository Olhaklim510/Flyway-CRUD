package com.company.Flyway_and_CRUD.services;

import com.company.Flyway_and_CRUD.prefs.Prefs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final Database INSTANCE = new Database();
    private Connection connection;

    private Database() {
        try {
            String connectionUrl = new Prefs ().getString(Prefs.DB_JDBC_CONNECTION_URL);
            connection = DriverManager.getConnection(connectionUrl);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public int executeUpdate (String sql){
        try(Statement st=connection.createStatement()){
           return st.executeUpdate(sql);
        } catch (Exception exc) {
            exc.printStackTrace();
            return -1;
        }
    }

    public Connection getConnection () throws Exception {
        return DriverManager.getConnection(new Prefs ().getString(Prefs.DB_JDBC_CONNECTION_URL));
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
