module com.example.cab302_study_buddy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cab302_study_buddy to javafx.fxml;
    exports com.example.cab302_study_buddy;
}