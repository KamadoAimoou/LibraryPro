package library.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import library.Main;
import library.model.User;
import library.service.UserService;
import library.utils.Alerts;
import library.utils.SessionManager;

import java.util.Optional;

public class LoginController {

    @FXML private TextField studentIdField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UserService userService = new UserService();

    @FXML
    public void handleLogin(ActionEvent event) {
        String studentId = studentIdField.getText() == null ? "" : studentIdField.getText().trim();
        String password  = passwordField.getText() == null ? "" : passwordField.getText();

        if (studentId.isBlank() || password.isBlank()) {
            errorLabel.setText("Please enter credentials.");
            return;
        }

        // отключаем UI, чтобы не жали 10 раз
        Node sourceBtn = (event.getSource() instanceof Node) ? (Node) event.getSource() : null;
        setUiDisabled(true, sourceBtn);
        errorLabel.setText("Signing in...");

        Task<Optional<User>> task = new Task<>() {
            @Override
            protected Optional<User> call() {
                return userService.authenticate(studentId, password); // БД тут, но уже НЕ в UI-потоке
            }
        };

        task.setOnSucceeded(e -> {
            Optional<User> userOpt = task.getValue();
            if (userOpt.isPresent()) {
                SessionManager.setCurrentUser(userOpt.get());
                Main.switchScene("/view/dashboard.fxml");
            } else {
                errorLabel.setText("Invalid student ID or password.");
                Alerts.error("Login failed");
                setUiDisabled(false, sourceBtn);
            }
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            ex.printStackTrace();
            errorLabel.setText("Database error. Try again.");
            Alerts.error("Database error");
            setUiDisabled(false, sourceBtn);
        });

        Thread t = new Thread(task, "auth-task");
        t.setDaemon(true);
        t.start();
    }

    private void setUiDisabled(boolean disabled, Node sourceBtn) {
        studentIdField.setDisable(disabled);
        passwordField.setDisable(disabled);
        if (sourceBtn != null) sourceBtn.setDisable(disabled);
    }
}