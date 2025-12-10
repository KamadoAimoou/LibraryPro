package library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            // !!! ВАША ЛОГИКА АУТЕНТИФИКАЦИИ ДОЛЖНА БЫТЬ ЗДЕСЬ !!!
            // Например: if (isValidUser(username, password)) { ... }

            System.out.println("Attempting login for user: " + username);

            // Временно считаем, что вход успешен, если поля не пустые
            loadDashboardScene();

        } else {
            // Добавьте сюда логику отображения ошибки (например, Label)
            System.out.println("Login Failed: Username or Password cannot be empty.");
        }
    }

    // library.controller.LoginController.java

    private void loadDashboardScene() {
        try {
            // 1. Загрузка FXML Дашборда (абсолютный путь)
            // ВНИМАНИЕ: Если этот путь не работает, перейдите к ШАГУ 2!
            Parent dashboardRoot = FXMLLoader.load(
                    getClass().getResource("/library/view/Dashboard.fxml")
            );

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene dashboardScene = new Scene(dashboardRoot);

            // 2. !!! ИСПРАВЛЕНИЕ: ПОДКЛЮЧАЕМ СТИЛИ К НОВОЙ СЦЕНЕ !!!
            // Путь к вашему CSS. Используем абсолютный путь, как в Main.java:
            String cssPath = "/library/view/main.css";

            if (getClass().getResource(cssPath) != null) {
                dashboardScene.getStylesheets().add(
                        getClass().getResource(cssPath).toExternalForm()
                );
            } else {
                System.err.println("❌ CSS ОШИБКА: Стили не найдены по пути: " + cssPath);
            }

            // 3. Смена сцены
            stage.setScene(dashboardScene);
            stage.setTitle("LibraryPro - Dashboard");
            stage.show();

        } catch (Exception e) {
            System.err.println("Failed to load the Dashboard scene.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        System.out.println("Register button clicked. Opening registration window...");
        // Здесь можно реализовать загрузку нового FXML для регистрации
    }
}