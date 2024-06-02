package com.example.cab302_study_buddy;

import javafx.collections.ObservableList;
//import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

import static com.example.cab302_study_buddy.TaskController.Task;
import static com.example.cab302_study_buddy.LoginController.current_user;

/**
 * Contains all logic required for the timer page functionality
 */
public class TimerController {

    // FXML fields representing the UI components
    @FXML
    private Label timerDisplay;

    @FXML
    private HBox timerControls;

    @FXML
    private VBox setTime;

    @FXML
    private Button setPauseBtn;

    @FXML
    private Button enterBtn;

    @FXML
    private TextField hoursInput;

    @FXML
    private Slider hourSlider;

    @FXML
    private TextField minutesInput;

    @FXML
    private Slider minuteSlider;

    @FXML
    private TextField secondsInput;

    @FXML
    private Slider secondSlider;

    @FXML
    private ComboBox taskComboBox;

    private boolean miniTimerWindowOpen = false;
    private Stage miniTimerStage;
    private TimerService timerService;

    /**
     * Method called when the FXML file is loaded
     */
    @FXML
    public void initialize() {
        timerService = TimerService.getInstance();
        timerDisplay.textProperty().bind(timerService.timerDisplayProperty());

        // Check the timer state and set the button text accordingly
        TimerService.TimerState currentState = timerService.getTimerState();
        if (currentState == TimerService.TimerState.RUNNING) {
            setPauseBtn.setText("Pause");
        } else if (currentState == TimerService.TimerState.PAUSED) {
            setPauseBtn.setText("Resume");
        } else {
            setPauseBtn.setText("Set");
        }

        // Populate tasks from the database into the ComboBox
        populateTasksComboBox();
    }

    /**
     * Method to populate the ComboBox with tasks from the database
     */
    private void populateTasksComboBox() {
        // Fetch tasks from the database
        ObservableList<com.example.cab302_study_buddy.TaskController.Task> tasks = DatabaseHandler.getTasksForUser(current_user.getId());

        // Clear existing items in the ComboBox
        taskComboBox.getItems().clear();

        // Add fetched tasks to the ComboBox
        taskComboBox.getItems().addAll(tasks);

        // Set a cell factory to display task names in the ComboBox
        taskComboBox.setCellFactory(param -> new ListCell<com.example.cab302_study_buddy.TaskController.Task>() {
            @Override
            protected void updateItem(com.example.cab302_study_buddy.TaskController.Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null || task.getTaskName() == null) {
                    setText(null);
                } else {
                    setText(task.getTaskName());
                }
            }
        });

        // Set a custom cell factory for rendering the selected item
        taskComboBox.setButtonCell(new ListCell<com.example.cab302_study_buddy.TaskController.Task>() {
            @Override
            protected void updateItem(com.example.cab302_study_buddy.TaskController.Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null || task.getTaskName() == null) {
                    setText(null);
                } else {
                    setText(task.getTaskName());
                }
            }
        });
    }

    /**
     * Method to handle task selection from the ComboBox
     */
    @FXML
    protected void onTaskSelected() {
        TaskController.Task selectedTask = (TaskController.Task) taskComboBox.getValue();
        if (selectedTask != null) {
            // Calculate the remaining time for the selected task
            int remainingTime = (int) (Double.parseDouble(selectedTask.getExpectedTime()) - Double.parseDouble(selectedTask.getCurrentTimeWorked()));
            if (remainingTime > 0) {
                // Set the initial timer value based on remaining time
                int hours = remainingTime / 3600;
                int minutes = (remainingTime % 3600) / 60;
                int seconds = remainingTime % 60;
                hoursInput.setText(String.format("%02d", hours));
                minutesInput.setText(String.format("%02d", minutes));
                secondsInput.setText(String.format("%02d", seconds));
            }
        }
    }

    /**
     * Helper method to format time as a two-digit string
     */
    private String FormatTime(TextField time) {
        if (time.getLength() == 2) {
            return time.getText();
        } else if (time.getLength() == 1) {
            return "0" + time.getText();
        } else {
            return "00";
        }
    }

    /**
     * Overloaded helper method to format time as a two-digit string
     */
    private String FormatTime(int timeInt) {
        String time = Integer.toString(timeInt);
        if (time.length() == 2) {
            return time;
        } else if (time.length() == 1) {
            return "0" + time;
        } else {
            return "00";
        }
    }

    /**
     * Method to handle the back button click
     */
    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage) timerDisplay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
    }

    /**
     * Method to handle the enter button click
     */
    @FXML
    protected void onEnterClick() {
        // Get the input time values
        int hours = Integer.parseInt(FormatTime(hoursInput));
        int minutes = Integer.parseInt(FormatTime(minutesInput));
        int seconds = Integer.parseInt(FormatTime(secondsInput));

        // Get the selected task
        Task selectedTask = (Task) taskComboBox.getValue();

        // Start the timer with the selected task and input time values
        timerService.startTimer(selectedTask, hours, minutes, seconds);

        timerControls.setVisible(true);
        setTime.setVisible(false);
        enterBtn.setVisible(false);
    }

    /**
     * Method to handle the set/pause button click
     */
    @FXML
    protected void onSetPauseClick() {
        if (Objects.equals(setPauseBtn.getText(), "Resume")) {
            timerService.resumeTimer();
            setPauseBtn.setText("Pause");
        } else if (Objects.equals(setPauseBtn.getText(), "Pause")) {
            timerService.pauseTimer();
            setPauseBtn.setText("Resume");
        } else { // == "Set"
            timerControls.setVisible(false);
            setTime.setVisible(true);
            enterBtn.setVisible(true);
            setPauseBtn.setText("Pause");
        }
    }

    /**
     * Method to handle the stop button click
     */
    @FXML
    protected void onStopClick() {
        timerService.stopTimer();
        setPauseBtn.setText("Set");
    }

    /**
     * Method to handle the mini timer button click
     */
    @FXML
    protected void onMiniTimerClick() {
        if (miniTimerWindowOpen) {
            return; // Mini timer window is already open
        }

        // Create a new stage for the mini timer
        miniTimerStage = new Stage();
        miniTimerStage.setTitle("Mini Timer");

        // Create the layout for the mini timer window
        VBox miniTimerRoot = new VBox();
        miniTimerRoot.setAlignment(Pos.CENTER);
        miniTimerRoot.setSpacing(15);
        Label miniTimerDisplay = new Label();
        miniTimerDisplay.textProperty().bind(timerService.timerDisplayProperty());
        miniTimerDisplay.setStyle("-fx-font-size: 48px;");

        // Create the controls for the mini timer window
        HBox miniTimerControls = new HBox();
        miniTimerControls.setAlignment(Pos.CENTER);
        miniTimerControls.setSpacing(10);
        Button miniPauseBtn = new Button("Pause");
        miniPauseBtn.setOnAction(e -> {
            if (miniPauseBtn.getText().equals("Pause")) {
                timerService.pauseTimer();
                miniPauseBtn.setText("Resume");
                setPauseBtn.setText("Resume");
            } else {
                timerService.resumeTimer();
                miniPauseBtn.setText("Pause");
                setPauseBtn.setText("Pause");
            }
        });
        Button miniStopBtn = new Button("Stop");
        miniStopBtn.setOnAction(e -> {
            onStopClick();
            miniTimerWindowOpen = false;
            miniTimerStage.close();
        });
        miniTimerControls.getChildren().addAll(miniPauseBtn, miniStopBtn);

        miniTimerRoot.getChildren().addAll(miniTimerDisplay, miniTimerControls);

        // Create the scene for the mini timer window
        Scene miniTimerScene = new Scene(miniTimerRoot, 320, 200);
        miniTimerStage.setScene(miniTimerScene);
        miniTimerStage.initStyle(StageStyle.UTILITY);
        miniTimerStage.setAlwaysOnTop(true);

        miniTimerStage.setOnCloseRequest(event -> miniTimerWindowOpen = false);

        miniTimerStage.show();
        miniTimerWindowOpen = true;
    }

    /**
     * Method to handle typing in the hours input field
     */
    @FXML
    protected void onHoursType() throws IOException {
        try {
            String lastChar = hoursInput.getText().substring(hoursInput.getLength() - 1);
            double newChar = Integer.parseInt(lastChar);
            double prevLastChar = hourSlider.getValue() % 10;
            double newValue = (prevLastChar * 10) + newChar;

            if (newValue > 99) {
                newValue = newChar;
            }

            hourSlider.adjustValue((int) newValue);
        } catch (NumberFormatException e) {
            // This is thrown when the String contains characters other than digits
            System.out.println("Invalid String");
        }

        hoursInput.setText(FormatTime((int) hourSlider.getValue()));
        hoursInput.positionCaret(2);
    }

    /**
     * Method to handle clicking the hours up button
     */
    @FXML
    protected void onHoursUpClick() throws IOException {
        // Increment the hour slider value
        if (hourSlider.getValue() == 99) {
            hourSlider.setValue(0);
        } else {
            hourSlider.increment();
        }

        hoursInput.setText(FormatTime((int) hourSlider.getValue()));
    }

    /**
     * Method to handle clicking the hours down button
     */
    @FXML
    protected void onHoursDownClick() throws IOException {
        // Decrement the hour slider value
        if (hourSlider.getValue() == 0) {
            hourSlider.setValue(99);
        } else {
            hourSlider.decrement();
        }

        hoursInput.setText(FormatTime((int) hourSlider.getValue()));
    }

    /**
     * Method to handle typing in the minutes input field
     */
    @FXML
    protected void onMinutesType() throws IOException {
        try {
            String lastChar = minutesInput.getText().substring(minutesInput.getLength() - 1);
            double newChar = Integer.parseInt(lastChar);
            double prevLastChar = minuteSlider.getValue() % 10;
            double newValue = (prevLastChar * 10) + newChar;

            if (newValue > 59) {
                newValue = newChar;
            }

            minuteSlider.adjustValue((int) newValue);
        } catch (NumberFormatException e) {
            // This is thrown when the String contains characters other than digits
            System.out.println("Invalid String");
        }

        minutesInput.setText(FormatTime((int) minuteSlider.getValue()));
        minutesInput.positionCaret(2);
    }

    /**
     * Method to handle clicking the minutes up button
     */
    @FXML
    protected void onMinutesUpClick() throws IOException {
        // Increment the minute slider value
        if (minuteSlider.getValue() == 59) {
            minuteSlider.setValue(0);
        } else {
            minuteSlider.increment();
        }

        minutesInput.setText(FormatTime((int) minuteSlider.getValue()));
    }

    /**
     * Method to handle clicking the minutes down button
     */
    @FXML
    protected void onMinutesDownClick() throws IOException {
        // Decrement the minute slider value
        if (minuteSlider.getValue() == 0) {
            minuteSlider.setValue(59);
        } else {
            minuteSlider.decrement();
        }

        minutesInput.setText(FormatTime((int) minuteSlider.getValue()));
    }

    /**
     * Method to handle typing in the seconds input field
     */
    @FXML
    protected void onSecondsType() throws IOException {
        try {
            String lastChar = secondsInput.getText().substring(secondsInput.getLength() - 1);
            double newChar = Integer.parseInt(lastChar);
            double prevLastChar = secondSlider.getValue() % 10;
            double newValue = (prevLastChar * 10) + newChar;

            if (newValue > 59) {
                newValue = newChar;
            }

            secondSlider.adjustValue((int) newValue);
        } catch (NumberFormatException e) {
            // This is thrown when the String contains characters other than digits
            System.out.println("Invalid String");
        }

        secondsInput.setText(FormatTime((int) secondSlider.getValue()));
        secondsInput.positionCaret(2);
    }

    /**
     * Method to handle clicking the seconds up button
     */
    @FXML
    protected void onSecondsUpClick() throws IOException {
        // Increment the second slider value
        if (secondSlider.getValue() == 59) {
            secondSlider.setValue(0);
        } else {
            secondSlider.increment();
        }

        secondsInput.setText(FormatTime((int) secondSlider.getValue()));
    }

    /**
     * Method to handle clicking the seconds down button
     */
    @FXML
    protected void onSecondsDownClick() throws IOException {
        // Decrement the second slider value
        if (secondSlider.getValue() == 0) {
            secondSlider.setValue(59);
        } else {
            secondSlider.decrement();
        }

        secondsInput.setText(FormatTime((int) secondSlider.getValue()));
    }
}