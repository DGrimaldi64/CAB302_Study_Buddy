
module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.cab302_study_buddy to javafx.fxml;
    exports com.example.cab302_study_buddy;
}