package library.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class BooksController {

    public StackPane contentArea;

    public void openForm() {
        try {
            Parent form = FXMLLoader.load(getClass().getResource("/library/view/book_form.fxml"));
            contentArea.getChildren().setAll(form);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
