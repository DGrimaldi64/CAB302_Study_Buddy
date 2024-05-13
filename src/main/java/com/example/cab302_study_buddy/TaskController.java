package com.example.cab302_study_buddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.cab302_study_buddy.LoginController.current_user;


/**
 * File to control fxml as well as interacting with database to  add, remove, update tasks table
 */
public class TaskController {
    @FXML
    private ListView<String> taskListView;
    @FXML
    private TextField addTaskTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button removeButton;
    @FXML
    private Label timerDisplay;

    private ObservableList<String> tasks = FXCollections.observableArrayList();
    private TextInputDialog editTaskDialog;
    private int currentUserId;

    public void initialize() {
        this.currentUserId = current_user.getId();
        DatabaseHandler.createTable(); // Create the tables if they don't exist
        tasks = DatabaseHandler.getTasksForUser(currentUserId); // Retrieve tasks for the current user
        taskListView.setItems(tasks);

        // Deselect any selected task when the text field or "Add" button is clicked
        addTaskTextField.setOnMouseClicked(event -> taskListView.getSelectionModel().clearSelection());
        addButton.setOnMouseClicked(event -> taskListView.getSelectionModel().clearSelection());
    }

    @FXML
    private void addTask() {
        String task = addTaskTextField.getText().trim();
        if (!task.isEmpty()) {
            System.out.println(current_user.getId());
            DatabaseHandler.insertTask(task, current_user.getId()); // Insert the new task for the current user
            tasks.add(task);
            taskListView.getItems().add(task); // Add the new task to the taskListView
            addTaskTextField.clear();
            taskListView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void showEditTaskDialog() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String selectedTask = tasks.get(selectedIndex);
            editTaskDialog = new TextInputDialog(selectedTask);
            editTaskDialog.setTitle("Edit Task");
            editTaskDialog.setHeaderText(null);
            editTaskDialog.setContentText("Edit the selected task:");
            editTaskDialog.showAndWait().ifPresent(updatedTask -> {
                if (!updatedTask.isEmpty()) {
                    tasks.set(selectedIndex, updatedTask);
                    DatabaseHandler.updateTask(updatedTask, currentUserId, selectedIndex + 1); // Update the task in the
                    // database
                    taskListView.getSelectionModel().clearSelection();
                }
            });
        } else {
            showAlert("No task selected", "Please select a task to update.");
        }
    }

    @FXML
    private void removeTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            String selectedTask = tasks.get(selectedIndex);
            tasks.remove(selectedIndex);
            DatabaseHandler.deleteTask(selectedTask, currentUserId);
            taskListView.getSelectionModel().clearSelection();
        } else {
            showAlert("No task selected", "Please select a task to remove.");
        }
    }

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage) timerDisplay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}