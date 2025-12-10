package library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.Node;

public class DashboardController {

    // Элементы FXML
    @FXML
    private VBox bookDisplay;

    @FXML
    private Label bookTitle, bookAuthor, bookType, bookPublished;

    @FXML
    private Label centerTitle; // Новый элемент для заголовка центральной панели

    // Метод инициализации вызывается после загрузки FXML
    @FXML
    public void initialize() {
        // Загрузить книги по умолчанию при старте
        showAvailableBooks();
    }

    private void updateCenterView(String title, Node... content) {
        centerTitle.setText(title);
        bookDisplay.getChildren().clear();
        bookDisplay.getChildren().add(centerTitle); // Всегда оставляем заголовок
        bookDisplay.getChildren().addAll(content);
    }

    private void clearBookDetails() {
        bookTitle.setText("Select a book");
        bookAuthor.setText("");
        bookType.setText("");
        bookPublished.setText("");
    }

    @FXML
    private void showAvailableBooks() {
        // Очистка и установка нового контента
        Label book1 = new Label("1. The Great Gatsby (Available)");
        Label book2 = new Label("2. To Kill a Mockingbird (Available)");

        updateCenterView("Available Books", book1, book2);
        clearBookDetails();

        // В реальном проекте здесь будет загрузка из БД и создание карточек книг
    }

    @FXML
    private void showIssueBooks() {
        Label issue1 = new Label("1. 1984 (Issued to John)");
        updateCenterView("Issued Books", issue1);
        clearBookDetails();
    }

    @FXML
    private void showReturnBooks() {
        Label returnLabel = new Label("Books pending return check...");
        updateCenterView("Return Management", returnLabel);
        clearBookDetails();
    }

    @FXML
    private void showSavedBooks() {
        Label savedLabel = new Label("You have no saved books.");
        updateCenterView("Saved Books", savedLabel);
        clearBookDetails();
    }

    @FXML
    private void handleSave() {
        System.out.println("Save clicked - Book ID: [Current Book ID]");
        // Логика сохранения выбранной книги в список пользователя
    }

    @FXML
    private void handleReturn() {
        System.out.println("Return clicked - Book ID: [Current Book ID]");
        // Логика пометки книги как возвращенной
    }
}