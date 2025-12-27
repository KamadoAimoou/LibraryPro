package library.dao.impl;

import library.config.Database;
import library.dao.BookDAO;
import library.model.Book;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAOImpl implements BookDAO {

    @Override
    public List<Book> findAll() {
        String sql = "SELECT id, title, author, type, published_date, available FROM books ORDER BY published_date DESC";
        return mapBooks(sql, null);
    }

    @Override
    public List<Book> search(String keyword) {
        String sql = """
        SELECT id, title, author, type, published_date, available
        FROM books
        WHERE LOWER(title) LIKE ?
        ORDER BY published_date DESC
        """;

        String like = "%" + (keyword == null ? "" : keyword.trim().toLowerCase()) + "%";

        return mapBooks(sql, stmt -> {
            try {
                stmt.setString(1, like);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Optional<Book> findById(int id) {
        String sql = "SELECT id, title, author, type, published_date, available FROM books WHERE id = ?";
        List<Book> books = mapBooks(sql, stmt -> {
            try {
                stmt.setInt(1, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return books.stream().findFirst();
    }

    @Override
    public void save(Book book) {
        String sql = "INSERT INTO books(title, author, type, published_date, available) VALUES(?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getType());
            ps.setDate(4, Date.valueOf(book.getPublishedDate()));
            ps.setBoolean(5, book.isAvailable());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE books SET title=?, author=?, type=?, published_date=?, available=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getType());
            ps.setDate(4, Date.valueOf(book.getPublishedDate()));
            ps.setBoolean(5, book.isAvailable());
            ps.setInt(6, book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> findAvailable() {
        String sql = """
        SELECT id, title, author, type, published_date, available
        FROM books
        WHERE available = true
        ORDER BY published_date DESC
        """;
        return mapBooks(sql, null);
    }

    @Override
    public void setAvailability(int bookId, boolean available) {
        String sql = "UPDATE books SET available = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, available);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private List<Book> mapBooks(String sql, StatementConfigurer configurer) {
        List<Book> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (configurer != null) {
                configurer.configure(ps);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("type"),
                        rs.getDate("published_date").toLocalDate(),
                        rs.getBoolean("available")
                );
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @FunctionalInterface
    private interface StatementConfigurer {
        void configure(PreparedStatement stmt);
    }
}

