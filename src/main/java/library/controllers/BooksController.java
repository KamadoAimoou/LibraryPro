package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import library.model.Book;
import library.service.BookService;
import library.service.SavedService;
import library.utils.Alerts;
import library.utils.SessionManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BooksController {

    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> typeColumn;
    @FXML private TableColumn<Book, LocalDate> dateColumn;
    @FXML private TableColumn<Book, Boolean> availableColumn;

    @FXML private TextField searchField;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField typeField;
    @FXML private DatePicker publishedPicker;

    @FXML private Button saveBtn;
    @FXML private Button deleteBtn;
    @FXML private Button favoriteBtn;

    private final BookService bookService = new BookService();
    private final SavedService savedService = new SavedService();
    private final ObservableList<Book> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // таблица
        titleColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));
        authorColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAuthor()));
        typeColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getType()));
        dateColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPublishedDate()));
        availableColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleBooleanProperty(c.getValue().isAvailable()));

        booksTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> populateForm(n));

        // ✅ ВАЖНО: принудительно включаем поля (чтобы можно было печатать)
        searchField.setDisable(false);
        searchField.setEditable(true);
        searchField.setFocusTraversable(true);

        boolean admin = SessionManager.isAdmin();

// 🔒 если НЕ админ — полностью убираем кнопки
        saveBtn.setVisible(admin);
        saveBtn.setManaged(admin);

        deleteBtn.setVisible(admin);
        deleteBtn.setManaged(admin);

        // кнопки
        saveBtn.setDisable(!admin);
        deleteBtn.setDisable(!admin);

        // ✅ и поля формы тоже выключаем/включаем корректно
        titleField.setDisable(!admin);
        authorField.setDisable(!admin);
        typeField.setDisable(!admin);
        publishedPicker.setDisable(!admin);

        publishedPicker.setValue(LocalDate.now());

        loadBooks();
    }

    private void loadBooks() {
        List<Book> books = bookService.findAll();
        data.setAll(books);
        booksTable.setItems(data);
        booksTable.refresh();
    }

    @FXML
    public void searchBooks() {
        String keyword = (searchField.getText() == null) ? "" : searchField.getText().trim();
        List<Book> books = bookService.search(keyword);
        data.setAll(books);
        booksTable.refresh();
    }

    @FXML
    public void saveBook() {
        if (!SessionManager.isAdmin()) {
            Alerts.error("Only admins can modify books.");
            return;
        }

        String title = titleField.getText() == null ? "" : titleField.getText().trim();
        String author = authorField.getText() == null ? "" : authorField.getText().trim();
        String type = typeField.getText() == null ? "" : typeField.getText().trim();
        LocalDate published = publishedPicker.getValue();

        // ✅ запрет на пустые значения (иначе и появляются пустые строки)
        if (title.isBlank() || author.isBlank() || type.isBlank() || published == null) {
            Alerts.error("Fill Title, Author, Type and Published date.");
            return;
        }

        Book selected = booksTable.getSelectionModel().getSelectedItem();
        Book book = (selected == null) ? new Book() : selected;

        book.setTitle(title);
        book.setAuthor(author);
        book.setType(type);
        book.setPublishedDate(published);

        // ⚠️ НЕ затираем available при update
        if (book.getId() == 0) {
            book.setAvailable(true);
            bookService.create(book);
            Alerts.info("Book added.");
        } else {
            bookService.update(book);
            Alerts.info("Book updated.");
        }

        clearForm();
        loadBooks();
    }

    @FXML
    public void deleteBook() {
        if (!SessionManager.isAdmin()) {
            Alerts.error("Only admins can delete books.");
            return;
        }
        Book selected = booksTable.getSelectionModel().getSelectedItem();
        if (selected != null && Alerts.confirm("Delete selected book?")) {
            bookService.delete(selected.getId());
            loadBooks();
        }
    }

    @FXML
    public void saveFavorite() {
        Book selected = booksTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alerts.error("Select a book to save.");
            return;
        }
        Optional.ofNullable(SessionManager.getCurrentUser())
                .ifPresent(user -> savedService.add(user.getId(), selected.getId()));
        Alerts.info("Book saved to favorites.");
    }

    private void populateForm(Book book) {
        if (book == null) {
            clearForm();
            return;
        }
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        typeField.setText(book.getType());
        publishedPicker.setValue(book.getPublishedDate());
    }

    private void clearForm() {
        titleField.clear();
        authorField.clear();
        typeField.clear();
        publishedPicker.setValue(LocalDate.now());
        booksTable.getSelectionModel().clearSelection();
    }
}