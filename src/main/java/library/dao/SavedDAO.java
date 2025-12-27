package library.dao;

import library.model.Book;

import java.util.List;

public interface SavedDAO {
    List<Book> findSavedByUser(int userId);

    void addFavorite(int userId, int bookId);

    void removeFavorite(int userId, int bookId);
}

