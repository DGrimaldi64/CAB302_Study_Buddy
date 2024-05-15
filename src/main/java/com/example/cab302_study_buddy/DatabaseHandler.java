package com.example.cab302_study_buddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Includes all methods to use the database, including connection parameters
 */
public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    private static Connection connection;

    public DatabaseHandler() {
        connection = DatabaseConnection.getInstance();
    }

    /**
     * Creates table with preset fields defined in the function itself
     */
    public static void createTable() {
        try {
            Connection connection = DatabaseConnection.getInstance();
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "username TEXT NOT NULL UNIQUE, " +
                            "password TEXT NOT NULL, " +
                            "identifier TEXT)");
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS tasks (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "task TEXT NOT NULL, " +
                            "user_id INTEGER NOT NULL, " +
                            "FOREIGN KEY(user_id) REFERENCES users(id))");
            close(connection);
        } catch (SQLException e) {
            showAlert("Error creating table: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Takes a username as string and after searching the database returns true or
     * false whether the row was found
     *
     * @param username the username of the account
     * @return bool
     * @throws SQLException Exception for errors when accessing database
     */
    public static boolean isUsernameExists(String username) throws SQLException {

        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement getAll = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            getAll.setString(1, username);
            ResultSet results = getAll.executeQuery();

            if (results.next()) {

                // Need to close connection each time after opening
                close(connection);

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            showAlert("Error checking whether username exists: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return false;
    }

    /**
     * Creates a new entry in the users.db database using the corresponding
     * parameters
     *
     * @param username   username of account
     * @param password   password of account
     * @param identifier identifier of account
     */
    public static void insertUser(String username, String password, String identifier) {
        try {
            Connection connection = DatabaseConnection.getInstance();
            if (!isUsernameExists(username)) {
                PreparedStatement stmt = connection
                        .prepareStatement("INSERT INTO users (username, password, identifier) VALUES (?, ?, ?)");

                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, identifier);
                stmt.execute();

            } else {
                showAlert("Username already exist: ", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            showAlert("Error inserting username: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Searches database for given username and returns the password if the field is
     * found
     *
     * @param username username of account
     * @return password as a string
     */
    public static String getPasswordForUsername(String username) {
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement getPassword = connection.prepareStatement("SELECT password FROM users WHERE username = ?");

            getPassword.setString(1, username);
            ResultSet rs = getPassword.executeQuery();

            if (rs.next()) {
                String password = rs.getString("password");
                System.out.println("Retrieved password from database: " + password);
                return password;
            }

        } catch (SQLException e) {
            showAlert("Error fetching password: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return null;
    }

    /**
     *
     * @param username
     * @return
     */
    public static int getIdForUsername(String username) {
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement getId = connection.prepareStatement("SELECT id FROM users WHERE username = ?");

            getId.setString(1, username);
            ResultSet rs = getId.executeQuery();

            if (rs.isBeforeFirst()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {

            showAlert("Error fetching password: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return 0;
    }

    public static void insertTask(String task, int userId) {
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO tasks (task, user_id) VALUES (?, ?)");
            stmt.setString(1, task);
            stmt.setInt(2, userId);
            stmt.execute();
        } catch (SQLException e) {
            showAlert("Error inserting task: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public static ObservableList<String> getTasksForUser(int userId) {
        ObservableList<String> tasks = FXCollections.observableArrayList();

        try {

            Connection connection = DatabaseConnection.getInstance();

            PreparedStatement getAll = connection
                    .prepareStatement("SELECT id, task FROM tasks WHERE user_id = ? ORDER BY id");
            getAll.setInt(1, userId);


            ResultSet results = getAll.executeQuery();

            while (results.next()) {
                tasks.add(results.getString("task"));
            }

            close(connection);
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println(e);
            showAlert("Error retrieving tasks: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return tasks;
    }

    public static void updateTask(String updatedTask, int userId, String currentTask) {
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement getTaskId = connection.prepareStatement("SELECT id FROM tasks WHERE task = ? AND user_id = ?");
            getTaskId.setString(1, currentTask);
            getTaskId.setInt(2, userId);
            ResultSet rs = getTaskId.executeQuery();

            if (rs.next()) {
                int taskId = rs.getInt("id");
                PreparedStatement stmt = connection.prepareStatement("UPDATE tasks SET task = ? WHERE id = ? AND user_id = ?");
                stmt.setString(1, updatedTask);
                stmt.setInt(2, taskId);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            } else {
                System.out.println("Task not found for user " + userId + " and task '" + currentTask + "'");
            }

            close(connection);
        } catch (SQLException e) {
            showAlert("Error updating task: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public static void deleteTask(String task, int userId) {
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM tasks WHERE task = ? AND user_id = ?");
            stmt.setString(1, task);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            close(connection);
        } catch (SQLException e) {
            showAlert("Error deleting task: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static boolean updatePassword(String username, String currentPassword, String newPassword) {
        try {
            Connection connection = DatabaseConnection.getInstance();

            if (connection == null) {
                System.out.println("Database connection is null");
                return false;
            }

            String storedPassword = getPasswordForUsername(username);
            System.out.println("Stored password: " + storedPassword);
            System.out.println("Provided current password (hashed): " + hashPassword(currentPassword));

            if (storedPassword == null || !storedPassword.equals(hashPassword(currentPassword))) {
                System.out.println("Password verification failed");
                return false;
            }

            PreparedStatement updatePassword = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            String hashedNewPassword = hashPassword(newPassword);
            updatePassword.setString(1, hashedNewPassword);
            updatePassword.setString(2, username);

            int rowsAffected = updatePassword.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated");

            close(connection);

            return rowsAffected > 0;
        } catch (SQLException e) {
            showAlert("Error updating password: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String hashedPassword = hexString.toString();
            System.out.println("Hashed password: " + hashedPassword);
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            showAlert("Error hashing password: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Creates pop-up in JavaFX to notify user of an error or other information
     *
     * @param message   message to display
     * @param alertType what type of alert is being used
     */
    private static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Closes a connection with the database
     *
     * @param connection variable of the connection
     */
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
