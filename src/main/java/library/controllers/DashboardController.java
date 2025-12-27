package library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import library.utils.SessionManager;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private StackPane contentArea;

    // ✅ сюда подставим sidebar вместо fx:include
    @FXML
    private VBox sidebarHost;

    @FXML
    public void initialize() {
        // ✅ 1) сначала подгружаем sidebar (чтобы его onAction работали)
        loadSidebar();

        // ✅ 2) потом welcome text
        if (SessionManager.getCurrentUser() != null) {
            welcomeLabel.setText("Welcome, " + SessionManager.getCurrentUser().getName());
        }

        // ✅ 3) дефолтный экран
        loadView("books.fxml");
    }

    private void loadSidebar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/sidebar.fxml"));
            loader.setController(this); // ✅ важно: sidebar использует этот же контроллер
            Node sidebar = loader.load();
            sidebarHost.getChildren().setAll(sidebar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openBooks() {
        loadView("books.fxml");
    }

    @FXML
    public void openIssue() {
        loadView("issue.fxml");
    }

    @FXML
    public void openReturn() {
        loadView("return.fxml");
    }

    @FXML
    public void openSaved() {
        loadView("saved.fxml");
    }

    @FXML
    public void logout() {
        SessionManager.setCurrentUser(null);
        library.Main.switchScene("/view/login.fxml");
    }

    private void loadView(String viewName) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/" + viewName));
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


