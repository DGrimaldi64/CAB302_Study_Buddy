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

public class TimerController {

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

    @FXML
    protected void onTaskSelected() {
        TaskController.Task selectedTask = (TaskController.Task) taskComboBox.getValue();
        if (selectedTask != null) {
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

    private String FormatTime(TextField time) {
        if (time.getLength() == 2) {
            return time.getText();
        } else if (time.getLength() == 1) {
            return "0" + time.getText();
        } else {
            return "00";
        }
    }

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

    @FXML
    protected void onBackClick() throws IOException {
        // change scene to Home
        Stage stage = (Stage) timerDisplay.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StudyBuddyApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),640, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
    }

    @FXML
    protected void onEnterClick() {
        int hours = Integer.parseInt(FormatTime(hoursInput));
        int minutes = Integer.parseInt(FormatTime(minutesInput));
        int seconds = Integer.parseInt(FormatTime(secondsInput));

        Task selectedTask = (Task) taskComboBox.getValue();
        timerService.startTimer(selectedTask, hours, minutes, seconds);

        timerControls.setVisible(true);
        setTime.setVisible(false);
        enterBtn.setVisible(false);
    }

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

    @FXML
    protected void onStopClick() {
        timerService.stopTimer();
        setPauseBtn.setText("Set");
    }

    @FXML
    protected void onMiniTimerClick() {
        if (miniTimerWindowOpen) {
            return; // Mini timer window is already open
        }

        // Create a new stage for the mini timer
        miniTimerStage = new Stage();
        miniTimerStage.setTitle("Mini Timer");

        VBox miniTimerRoot = new VBox();
        miniTimerRoot.setAlignment(Pos.CENTER);
        miniTimerRoot.setSpacing(15);
        Label miniTimerDisplay = new Label();
        miniTimerDisplay.textProperty().bind(timerService.timerDisplayProperty());
        miniTimerDisplay.setStyle("-fx-font-size: 48px;");

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
            timerService.stopTimer();
            miniTimerWindowOpen = false;
            miniTimerStage.close();
        });
        miniTimerControls.getChildren().addAll(miniPauseBtn, miniStopBtn);

        miniTimerRoot.getChildren().addAll(miniTimerDisplay, miniTimerControls);

        Scene miniTimerScene = new Scene(miniTimerRoot, 320, 200);
        miniTimerStage.setScene(miniTimerScene);
        miniTimerStage.initStyle(StageStyle.UTILITY);
        miniTimerStage.setAlwaysOnTop(true);

        miniTimerStage.setOnCloseRequest(event -> miniTimerWindowOpen = false);

        miniTimerStage.show();
        miniTimerWindowOpen = true;
    }

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

    @FXML
    protected void onHoursUpClick() throws IOException {
        // increment timer
        if (hourSlider.getValue() == 99) {
            hourSlider.setValue(0);
        } else {
            hourSlider.increment();
        }

        hoursInput.setText(FormatTime((int) hourSlider.getValue()));
    }

    @FXML
    protected void onHoursDownClick() throws IOException {
        // decrement timer
        if (hourSlider.getValue() == 0) {
            hourSlider.setValue(99);
        } else {
            hourSlider.decrement();
        }

        hoursInput.setText(FormatTime((int) hourSlider.getValue()));
    }

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

    @FXML
    protected void onMinutesUpClick() throws IOException {
        // increment timer
        if (minuteSlider.getValue() == 59) {
            minuteSlider.setValue(0);
        } else {
            minuteSlider.increment();
        }

        minutesInput.setText(FormatTime((int) minuteSlider.getValue()));
    }

    @FXML
    protected void onMinutesDownClick() throws IOException {
        // decrement timer
        if (minuteSlider.getValue() == 0) {
            minuteSlider.setValue(59);
        } else {
            minuteSlider.decrement();
        }

        minutesInput.setText(FormatTime((int) minuteSlider.getValue()));
    }

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

    @FXML
    protected void onSecondsUpClick() throws IOException {
        // increment timer
        if (secondSlider.getValue() == 59) {
            secondSlider.setValue(0);
        } else {
            secondSlider.increment();
        }

        secondsInput.setText(FormatTime((int) secondSlider.getValue()));
    }

    @FXML
    protected void onSecondsDownClick() throws IOException {
        // decrement timer
        if (secondSlider.getValue() == 0) {
            secondSlider.setValue(59);
        } else {
            secondSlider.decrement();
        }

        secondsInput.setText(FormatTime((int) secondSlider.getValue()));
    }
}