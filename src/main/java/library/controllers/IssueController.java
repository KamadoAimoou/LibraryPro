package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import library.model.Book;
import library.service.BookService;
import library.service.IssueService;
import library.utils.Alerts;
import library.utils.SessionManager;

import java.time.LocalDate;
import java.util.List;

public class IssueController {

    @FXML private TableView<Book> availableTable;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> typeColumn;

    @FXML private DatePicker dueDatePicker;
    @FXML private Button issueBtn;

    private final BookService bookService = new BookService();
    private final IssueService issueService = new IssueService();
    private final ObservableList<Book> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));
        authorColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getAuthor()));
        typeColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getType()));

        dueDatePicker.setValue(LocalDate.now().plusDays(14));
        issueBtn.setDisable(!SessionManager.isAdmin());

        loadAvailableBooks();
    }

    private void loadAvailableBooks() {
        List<Book> books = bookService.findAvailable();
        data.setAll(books);
        availableTable.setItems(data);
    }

    @FXML
    public void issueSelected() {
        if (!SessionManager.isAdmin()) {
            Alerts.error("Only admins can issue books.");
            return;
        }

        Book selected = availableTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alerts.error("Select a book to issue.");
            return;
        }

        LocalDate dueDate = dueDatePicker.getValue();
        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            Alerts.error("Invalid due date.");
            return;
        }

        int userId = SessionManager.getCurrentUser().getId();

        boolean ok = issueService.issueBook(userId, selected.getId(), dueDate);
        if (!ok) {
            Alerts.error("Cannot issue this book (maybe already issued).");
            return;
        }

        Alerts.info("Book issued successfully.");
        loadAvailableBooks();
    }
}