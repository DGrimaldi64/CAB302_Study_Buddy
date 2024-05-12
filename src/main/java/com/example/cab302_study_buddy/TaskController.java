package com.example.cab302_study_buddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private ObservableList<String> tasks = FXCollections.observableArrayList();
    private TextInputDialog editTaskDialog;
    private int currentUserId;

    public void initialize(int userId) {
        this.currentUserId = userId;
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
            DatabaseHandler.insertTask(task, currentUserId); // Insert the new task for the current user
            tasks.add(task); // No need to add task numbers here
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
                    DatabaseHandler.updateTask(updatedTask, currentUserId, selectedIndex + 1); // Update the task in the database
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
        if (selectedIndex >= 0) {
            String taskToRemove = tasks.get(selectedIndex);
            DatabaseHandler.deleteTask(taskToRemove, currentUserId); // Delete the task from the database
            tasks.remove(selectedIndex);
        } else {
            showAlert("No task selected", "Please select a task to remove.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}