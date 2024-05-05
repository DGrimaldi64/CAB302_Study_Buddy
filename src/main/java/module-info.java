module com.example.database_workinggit {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.database_workinggit to javafx.fxml;
    exports com.example.database_workinggit;
}