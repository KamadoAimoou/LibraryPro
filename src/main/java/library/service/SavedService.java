package library.service;

import library.dao.SavedDAO;
import library.dao.impl.SavedDAOImpl;
import library.model.Book;

import java.util.List;

public class SavedService {
    private final SavedDAO savedDAO = new SavedDAOImpl();

    public List<Book> getFavorites(int userId) {
        return savedDAO.findSavedByUser(userId);
    }

    public void add(int userId, int bookId) {
        savedDAO.addFavorite(userId, bookId);
    }

    public void remove(int userId, int bookId) {
        savedDAO.removeFavorite(userId, bookId);
    }
}

