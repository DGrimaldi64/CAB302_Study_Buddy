package com.example.cab302_study_buddy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class which creates a singleton variable for connection to the database
 */
public class DatabaseConnection {
    private static Connection instance = null;

    /**
     * Private constructor for the class publically accessible through getInstance() method
     */
    DatabaseConnection() {
        String url = "jdbc:sqlite:users.db";
        try {
            instance = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Attempts to obtain instance of database singleton
     * @return returns a variable of type "Connection"
     */
    public static Connection getInstance() {
        System.out.println(instance);
        try {
            if (instance ==null || instance.isClosed()) {
                new DatabaseConnection();
            }
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
        return instance;
    }
}
