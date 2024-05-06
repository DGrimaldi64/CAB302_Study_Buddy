<<<<<<< HEAD
module com.example.cab302_study_buddy {
    requires javafx.controls;
    requires javafx.fxml;
=======
module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
>>>>>>> 1bd00ee682beefce036ea191575eaddc6cac4a19


    opens com.example.cab302_study_buddy to javafx.fxml;
    exports com.example.cab302_study_buddy;
}