package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import library.model.Book;
import library.service.SavedService;
import library.utils.Alerts;
import library.utils.SessionManager;

public class SavedController {

    @FXML
    private TableView<Book> savedTable;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> typeColumn;
    @FXML
    private Button removeBtn;

    private final SavedService savedService = new SavedService();
    private final ObservableList<Book> favorites = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTitle()));
        authorColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getAuthor()));
        typeColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getType()));
        loadFavorites();
    }

    private void loadFavorites() {
        if (SessionManager.getCurrentUser() == null) {
            return;
        }
        favorites.setAll(savedService.getFavorites(SessionManager.getCurrentUser().getId()));
        savedTable.setItems(favorites);
    }

    @FXML
    public void removeFavorite() {
        Book selected = savedTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alerts.error("Select a favorite to remove.");
            return;
        }
        savedService.remove(SessionManager.getCurrentUser().getId(), selected.getId());
        Alerts.info("Removed from favorites.");
        loadFavorites();
    }
}

