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

    private ObservableList<String> tasks;
    private TextInputDialog editTaskDialog;

    public void initialize() {
        tasks = FXCollections.observableArrayList();
        taskListView.setItems(tasks);

        // Deselect any selected task when the text field or "Add" button is clicked
        addTaskTextField.setOnMouseClicked(event -> taskListView.getSelectionModel().clearSelection());
        addButton.setOnMouseClicked(event -> taskListView.getSelectionModel().clearSelection());
    }

    @FXML
    private void addTask() {
        String task = addTaskTextField.getText().trim();
        if (!task.isEmpty()) {
            tasks.add((tasks.size() + 1) + ". " + task);
            addTaskTextField.clear();
            taskListView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void showEditTaskDialog() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String selectedTask = tasks.get(selectedIndex);
            String taskText = selectedTask.substring(selectedTask.indexOf(".") + 2);
            editTaskDialog = new TextInputDialog(taskText);
            editTaskDialog.setTitle("Edit Task");
            editTaskDialog.setHeaderText(null);
            editTaskDialog.setContentText("Edit the selected task:");
            editTaskDialog.showAndWait().ifPresent(updatedTask -> {
                if (!updatedTask.isEmpty()) {
                    tasks.set(selectedIndex, (selectedIndex + 1) + ". " + updatedTask);
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
            tasks.remove(selectedIndex);
            updateTaskNumbers();
        } else {
            showAlert("No task selected", "Please select a task to remove.");
        }
    }

    private void updateTaskNumbers() {
        ObservableList<String> updatedTasks = FXCollections.observableArrayList();
        for (int i = 0; i < tasks.size(); i++) {
            updatedTasks.add((i + 1) + ". " + tasks.get(i).substring(tasks.get(i).indexOf(".") + 2));
        }
        tasks.clear();
        tasks.addAll(updatedTasks);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}     
