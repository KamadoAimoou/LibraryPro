package library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BookFormController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private void saveBook() {
        System.out.println("Saved: " + titleField.getText());
    }
}
