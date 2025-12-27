package library.dao.impl;

import library.config.Database;
import library.dao.SavedDAO;
import library.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavedDAOImpl implements SavedDAO {
    @Override
    public List<Book> findSavedByUser(int userId) {
        String sql = "SELECT b.id, b.title, b.author, b.type, b.published_date, b.available FROM favorites f JOIN books b ON f.book_id = b.id WHERE f.user_id = ? ORDER BY b.title";
        List<Book> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
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

    @Override
    public void addFavorite(int userId, int bookId) {
        String sql = "INSERT INTO favorites(user_id, book_id) VALUES(?,?) ON CONFLICT DO NOTHING";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFavorite(int userId, int bookId) {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND book_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

