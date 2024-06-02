package com.example.cab302_study_buddy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static com.example.cab302_study_buddy.LoginController.current_user;


/**
 * File to control fxml as well as interacting with database to  add, remove, update tasks table
 */
public class TaskController {
    @FXML
    private TableView<Task> taskTableView;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<Task, String> expectedTimeColumn;
    @FXML
    private TableColumn<Task, String> currentTimeWorkedColumn;
    @FXML
    private TableColumn<Task, Boolean> activeColumn; // New column for active status
    @FXML
    private TextField addTaskTextField;
    @FXML
    private TextField addDurationTextField;
    @FXML
    private Button addButton;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private int currentUserId;

    public void initialize() {
        this.currentUserId = current_user.getId();
        DatabaseHandler.createTable(); // Create the tables if they don't exist
        tasks = DatabaseHandler.getTasksForUser(currentUserId); // Retrieve tasks for the current user
        taskTableView.setItems(tasks);

        // Initialize the columns
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        expectedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("expectedTime"));
        currentTimeWorkedColumn.setCellValueFactory(new PropertyValueFactory<>("currentTimeWorked"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active")); // Link activeColumn to the 'active' property of Task

        // Deselect any selected task when the text field or "Add" button is clicked
        addTaskTextField.setOnMouseClicked(event -> taskTableView.getSelectionModel().clearSelection());
        addButton.setOnMouseClicked(event -> taskTableView.getSelectionModel().clearSelection());
    }

    @FXML
    private void addTask() {
        String taskName = addTaskTextField.getText().trim();
        String durationText = addDurationTextField.getText().trim();
        if (!taskName.isEmpty() && durationText.matches("\\d+")) {
            double duration = Double.parseDouble(durationText);
            // For simplicity, set active status to true when adding a new task
            tasks.add(new Task(taskName, String.valueOf(duration), "0", true)); // Adding a new task with 0 current time worked and active status true
            DatabaseHandler.insertTask(taskName, current_user.getId(), duration); // Insert the new task for the current user
            addTaskTextField.clear();
            addDurationTextField.clear();
            taskTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void showEditTaskDialog() {
        int selectedIndex = taskTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Task selectedTask = tasks.get(selectedIndex);

            // Create a dialog for editing the task
            Dialog<Task> editTaskDialog = new Dialog<>();
            editTaskDialog.setTitle("Edit Task");
            editTaskDialog.setHeaderText(null);

            // Set the content of the dialog
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField taskNameField = new TextField(selectedTask.getTaskName());
            TextField expectedCompletionTimeField = new TextField(selectedTask.getExpectedTime());
            TextField currentTimeWorkedField = new TextField(selectedTask.getCurrentTimeWorked());
            CheckBox activeCheckBox = new CheckBox("Active");
            activeCheckBox.setSelected(selectedTask.isActive());

            grid.add(new Label("Task Name:"), 0, 0);
            grid.add(taskNameField, 1, 0);
            grid.add(new Label("Expected Completion Time:"), 0, 1);
            grid.add(expectedCompletionTimeField, 1, 1);
            grid.add(new Label("Current Time Worked:"), 0, 2);
            grid.add(currentTimeWorkedField, 1, 2);
            grid.add(activeCheckBox, 1, 3);

            editTaskDialog.getDialogPane().setContent(grid);

            // Add buttons to the dialog
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            editTaskDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

            // Handle button actions
            editTaskDialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    return new Task(taskNameField.getText(), expectedCompletionTimeField.getText(), currentTimeWorkedField.getText(), activeCheckBox.isSelected());
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<Task> result = editTaskDialog.showAndWait();
            result.ifPresent(updatedTask -> {
                if (updatedTask != null) {
                    tasks.set(selectedIndex, updatedTask);
                    DatabaseHandler.updateTask(updatedTask.getTaskName(), currentUserId, selectedTask.getTaskName(),
                            Double.parseDouble(updatedTask.getExpectedTime()), Double.parseDouble(updatedTask.getCurrentTimeWorked()), updatedTask.isActive());
                    taskTableView.getSelectionModel().clearSelection();
                }
            });
        } else {
            showAlert("No task selected", "Please select a task to update.");
        }
    }

    @FXML
    private void removeTask() {
        int selectedIndex = taskTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            Task selectedTask = tasks.get(selectedIndex);
            tasks.remove(selectedIndex);
            DatabaseHandler.deleteTask(selectedTask.getTaskName(), currentUserId);
            taskTableView.getSelectionModel().clearSelection();
        } else {
            showAlert("No task selected", "Please select a task to remove.");
        }
    }

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage) taskTableView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Task {
        private final String taskName;
        private final String expectedTime;
        private final String currentTimeWorked;
        private final boolean active;

        public Task(String taskName, String expectedTime, String currentTimeWorked, boolean active) {
            this.taskName = taskName;
            this.expectedTime = expectedTime;
            this.currentTimeWorked = currentTimeWorked;
            this.active = active;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getExpectedTime() {
            return expectedTime;
        }

        public String getCurrentTimeWorked() {
            return currentTimeWorked;
        }

        public boolean isActive() {
            return active;
        }
    }
}