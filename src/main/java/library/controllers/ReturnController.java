package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.model.IssuedBook;
import library.service.IssueService;
import library.utils.Alerts;

import java.time.LocalDate;

public class ReturnController {

    @FXML
    private TableView<IssuedBook> issuedTable;

    @FXML
    private TableColumn<IssuedBook, Integer> bookIdColumn;

    @FXML
    private TableColumn<IssuedBook, Integer> userIdColumn;

    @FXML
    private TableColumn<IssuedBook, LocalDate> issueDateColumn;

    @FXML
    private TableColumn<IssuedBook, LocalDate> dueDateColumn;

    @FXML
    private Button returnBtn;

    private final IssueService issueService = new IssueService();

    private final ObservableList<IssuedBook> issuedBooks =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        issuedTable.setItems(issuedBooks);
        loadIssues();
    }

    private void loadIssues() {
        issuedBooks.setAll(issueService.activeIssues());
    }

    @FXML
    public void markReturned() {
        IssuedBook selected = issuedTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alerts.error("Select a record to return.");
            return;
        }

        issueService.returnBook(selected.getId(), selected.getBookId());
        Alerts.info("Book marked as returned.");
        loadIssues();
    }
}