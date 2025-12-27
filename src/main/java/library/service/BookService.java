package library.service;

import library.dao.BookDAO;
import library.dao.impl.BookDAOImpl;
import library.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookDAO bookDAO = new BookDAOImpl();
    private final HashMap<Integer, Book> cache = new HashMap<>();

    public List<Book> findAll() {
        List<Book> books = bookDAO.findAll();
        rebuildCache(books);
        return new ArrayList<>(books);
    }

    public List<Book> findAvailable() {
        List<Book> books = bookDAO.findAvailable();
        rebuildCache(books);
        return new ArrayList<>(books);
    }

    public List<Book> search(String keyword) {
        String k = (keyword == null) ? "" : keyword.trim();
        if (k.isBlank()) return findAll();

        List<Book> books = bookDAO.search(k);
        for (Book b : books) cache.put(b.getId(), b);
        return books;
    }

    public Optional<Book> getById(int id) {
        if (cache.containsKey(id)) return Optional.of(cache.get(id));
        Optional<Book> book = bookDAO.findById(id);
        book.ifPresent(b -> cache.put(id, b));
        return book;
    }

    public void create(Book book) {
        bookDAO.save(book);
        cache.clear();
    }

    public void update(Book book) {
        bookDAO.update(book);
        cache.put(book.getId(), book);
    }

    public void delete(int id) {
        bookDAO.delete(id);
        cache.remove(id);
    }

    public void setAvailability(int bookId, boolean available) {
        // ✅ обновляем сразу в БД (надежнее, чем через cache/getById)
        bookDAO.setAvailability(bookId, available);

        // обновим кэш если есть
        if (cache.containsKey(bookId)) {
            cache.get(bookId).setAvailable(available);
        }
    }

    private void rebuildCache(List<Book> books) {
        cache.clear();
        for (Book b : books) cache.put(b.getId(), b);
    }
}