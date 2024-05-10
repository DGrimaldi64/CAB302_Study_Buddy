
module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.cab302_study_buddy to javafx.fxml;
    exports com.example.cab302_study_buddy;
    exports com.example.cab302_study_buddy.database;
    opens com.example.cab302_study_buddy.database to javafx.fxml;
}