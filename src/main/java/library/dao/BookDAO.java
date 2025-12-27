package library.dao;

import library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
    List<Book> findAll();

    List<Book> search(String keyword);

    Optional<Book> findById(int id);

    List<Book> findAvailable();
    void setAvailability(int bookId, boolean available);

    void save(Book book);

    void update(Book book);

    void delete(int id);
}

