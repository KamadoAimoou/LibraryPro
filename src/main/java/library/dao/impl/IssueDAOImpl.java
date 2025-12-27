package library.dao.impl;

import library.config.Database;
import library.dao.IssueDAO;
import library.model.IssuedBook;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IssueDAOImpl implements IssueDAO {

    @Override
    public void issueBook(IssuedBook issuedBook) {
        String sql = "INSERT INTO issued_books(book_id, user_id, issue_date, due_date, returned) VALUES (?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, issuedBook.getBookId());
            ps.setInt(2, issuedBook.getUserId());
            ps.setDate(3, Date.valueOf(issuedBook.getIssueDate()));
            ps.setDate(4, Date.valueOf(issuedBook.getDueDate()));
            ps.setBoolean(5, issuedBook.isReturned());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<IssuedBook> findActive() {
        String sql = "SELECT id, book_id, user_id, issue_date, due_date, returned FROM issued_books WHERE returned = false ORDER BY issue_date DESC";
        List<IssuedBook> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                IssuedBook ib = new IssuedBook(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getBoolean("returned")
                );
                list.add(ib);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void markReturned(int issueId) {
        String sql = "UPDATE issued_books SET returned = true WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

