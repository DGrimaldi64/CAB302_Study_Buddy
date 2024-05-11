package com.example.cab302_study_buddy;


import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    private static Connection connection;

    public DatabaseHandler() {
        connection = DatabaseConnection.getInstance();
    }

    public void createTable() {
        try {
            Connection connection = DatabaseConnection.getInstance();
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "username TEXT NOT NULL UNIQUE, " +
                            "password TEXT NOT NULL, " +
                            "identifier TEXT)"
            );
            // Need to close connection each time after opening
            close(connection);

        } catch (SQLException e) {
            showAlert("Error creating table: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static boolean isUsernameExists(String username) throws SQLException {

        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement getAll = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            getAll.setString(1, username);
            ResultSet results = getAll.executeQuery();

            if (results.next()) {
                UserLogin found_user = new UserLogin(
                        results.getInt("id"),
                        results.getString("username"),
                        results.getString("password"),
                        results.getString("identifier")
                );
                // Need to close connection each time after opening
                close(connection);

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
//            showAlert("Error checking whether username exists: " + e.getMessage() , Alert.AlertType.ERROR);
        }

        return false;
    }

    public static void insertUser(String username, String password, String identifier) {
        try {
            Connection connection = DatabaseConnection.getInstance();
            if (!isUsernameExists(username)){
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (username, password, identifier) VALUES (?, ?, ?)");

                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, identifier);
                stmt.execute();

                // Need to close connection each time after opening
                close(connection);
            }
            else {
//                showAlert("Username already exist: ", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
//            showAlert("Error inserting username: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static String getPasswordForUsername(String username) {
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement getPassword = connection.prepareStatement("SELECT password FROM users WHERE username = ?");

            getPassword.setString(1, username);
            ResultSet rs = getPassword.executeQuery();


            return rs.getString("password");



        } catch (SQLException e) {
//            showAlert("Error fetching password: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return null;
    }

    private static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}