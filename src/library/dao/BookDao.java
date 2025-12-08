package library.dao;

import library.model.Book;
import library.model.Status;
import library.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao {

    // Вспомогательный метод для маппинга ResultSet на объект Book
    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("genre"),
                rs.getInt("year"),
                rs.getString("status")
        );
    }

    // 1. CREATE (Сохранение)
    public Book save(Book book) {
        // RETURNING id - особенность Postgres для получения сгенерированного ключа
        String sql = "INSERT INTO books (title, author, genre, year, status) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getGenre());
            stmt.setInt(4, book.getYear());
            stmt.setString(5, Status.AVAILABLE.getDbValue());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    book.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving book: " + e.getMessage());
        }
        return book;
    }

    // 2. READ (Получение всех)
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";
        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all books: " + e.getMessage());
        }
        return books;
    }

    // 3. READ (Получение по ID)
    public Optional<Book> findById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractBookFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding book by id: " + e.getMessage());
        }
        return Optional.empty();
    }

    // 4. UPDATE (Обновление статуса, используется Человеком 3)
    public void updateStatus(int bookId, Status status) {
        String sql = "UPDATE books SET status = ? WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.getDbValue());
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating book status: " + e.getMessage());
        }
    }

    // 5. DELETE
    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }
}