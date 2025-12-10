package library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;

public class BooksController {

    @FXML
    private Button btnBack;
    @FXML
    private Button btnAddBook;
    @FXML
    private TableView booksTable;

    // Относительный путь к общему файлу стилей
    private final String CSS_PATH = "../../view/main.css";

    @FXML
    public void goBack() {
        try {
            // Загрузка Dashboard.fxml с ОТНОСИТЕЛЬНЫМ путем
            Parent dashboardRoot = FXMLLoader.load(
                    getClass().getResource("../../view/Dashboard.fxml")
            );
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Scene scene = new Scene(dashboardRoot);

            // ПОДКЛЮЧАЕМ CSS К СЦЕНЕ ДАШБОРДА
            if (getClass().getResource(CSS_PATH) != null) {
                scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            }

            stage.setScene(scene);
            stage.setTitle("LibraryPro - Dashboard");
        } catch (IOException e) {
            System.err.println("Failed to load Dashboard scene.");
            e.printStackTrace();
        }
    }

    @FXML
    public void openBookForm() {
        try {
            // Загрузка book_form.fxml с ОТНОСИТЕЛЬНЫМ путем
            Parent formRoot = FXMLLoader.load(
                    getClass().getResource("../../view/book_form.fxml")
            );
            Stage stage = (Stage) btnAddBook.getScene().getWindow();
            Scene scene = new Scene(formRoot);

            // ПОДКЛЮЧАЕМ CSS К СЦЕНЕ ФОРМЫ КНИГИ
            if (getClass().getResource(CSS_PATH) != null) {
                scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
            }

            stage.setScene(scene);
            stage.setTitle("LibraryPro - Add Book");
        } catch (IOException e) {
            System.err.println("Failed to load Book Form scene.");
            e.printStackTrace();
        }
    }
}