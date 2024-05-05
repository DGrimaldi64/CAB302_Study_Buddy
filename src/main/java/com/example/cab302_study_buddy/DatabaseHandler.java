package com.example.cab302_study_buddy;


import javafx.scene.control.Alert;

import java.sql.*;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public static void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "identifier TEXT)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableQuery);
        } catch (SQLException e) {
            showAlert("Error creating table: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static boolean isUsernameExists(String username) {
        String selectQuery = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

            stmt.setString(1, username);
            return stmt.executeQuery().getInt(1) > 0;

        } catch (SQLException e) {
            showAlert("Error checking username: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    public static void insertUser(String username, String password, String identifier) {
        String insertQuery = "INSERT INTO users (username, password, identifier) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, identifier);
            stmt.executeUpdate();

        } catch (SQLException e) {
            showAlert("Username already exist: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static String getPasswordForUsername(String username) {
        String selectQuery = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (SQLException e) {
            showAlert("Error fetching password: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return null;
    }

    private static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}