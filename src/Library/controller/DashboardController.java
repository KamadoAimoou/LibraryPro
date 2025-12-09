package library.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class DashboardController {

    public StackPane contentArea;

    private void setView(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/library/view/" + fxml));
            contentArea.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDashboard() {
        setView("books.fxml");
    }

    public void openBooks() {
        setView("books.fxml");
    }

    public void openBookForm() {
        setView("book_form.fxml");
    }
}
