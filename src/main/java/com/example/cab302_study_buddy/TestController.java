package com.example.cab302_study_buddy;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TestController {

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.getSource() == textField1) {
                textField2.requestFocus();
            } else if (event.getSource() == textField2) {
                textField1.requestFocus();
            }
        }
    }
}