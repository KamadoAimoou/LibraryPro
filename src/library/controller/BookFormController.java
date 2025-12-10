package library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;

public class BookFormController {

    // Связываем FXML элементы
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField isbnField;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnBack;

    // Относительный путь к CSS, как и в других контроллерах
    private final String CSS_PATH = "../../view/main.css";

    @FXML
    public void saveBook() {
        // ... (Ваша логика валидации остается прежней) ...
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();

        if (title.isEmpty() || author.isEmpty() || year.isEmpty()) {
            System.err.println("❌ Ошибка: Название, Автор и Год обязательны для заполнения!");
            return;
        }

        System.out.println("📘 Книга сохранена: " + title + " by " + author);

        // !!! ЗДЕСЬ ДОЛЖНА БЫТЬ ЛОГИКА СОХРАНЕНИЯ В МОДЕЛЬ ИЛИ БАЗУ ДАННЫХ !!!

        // После сохранения возвращаемся на предыдущий экран
        goBack();
    }

    @FXML
    public void goBack() {
        try {
            // Загрузка Dashboard.fxml с ОТНОСИТЕЛЬНЫМ путем
            Parent dashboardRoot = FXMLLoader.load(
                    getClass().getResource("../../view/Dashboard.fxml")
            );
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Scene scene = new Scene(dashboardRoot);

            // ПОДКЛЮЧАЕМ CSS К НОВОЙ СЦЕНЕ
            if (getClass().getResource(CSS_PATH) != null) {
                scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            }

            stage.setScene(scene);
            stage.setTitle("LibraryPro - Dashboard");
        } catch (IOException e) {
            System.err.println("Failed to load the Dashboard scene.");
            e.printStackTrace();
        }
    }
}