package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskManager extends Application {
    private ListView<String> taskListView;
    private ObservableList<String> tasks;
    private TextField addTaskTextField;
    private TextInputDialog editTaskDialog;

    @Override
    public void start(Stage primaryStage) {
        tasks = FXCollections.observableArrayList();
        taskListView = new ListView<>(tasks);
        addTaskTextField = new TextField();
        addTaskTextField.setPromptText("Write your task here...");
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addTask());

        Button updateButton = new Button("Update Task");
        updateButton.setOnAction(e -> showEditTaskDialog());

        Button removeButton = new Button("Remove Task");
        removeButton.setOnAction(e -> removeTask());

        HBox inputArea = new HBox(10, addTaskTextField, addButton);
        HBox buttonArea = new HBox(10, updateButton, removeButton);

        VBox root = new VBox(10, inputArea, taskListView, buttonArea);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Deselect any selected task when the text field or "Add" button is clicked
        addTaskTextField.setOnMouseClicked(event -> taskListView.getSelectionModel().clearSelection());
        addButton.setOnMouseClicked(event -> taskListView.getSelectionModel().clearSelection());
    }

    private void addTask() {
        String task = addTaskTextField.getText().trim();
        if (!task.isEmpty()) {
            tasks.add((tasks.size() + 1) + ". " + task);
            addTaskTextField.clear();
            taskListView.getSelectionModel().clearSelection();
        }
    }

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

    public static void main(String[] args) {
        launch(args);
    }
}