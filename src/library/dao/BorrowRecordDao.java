package library.dao;

import library.model.BorrowRecord;
import library.util.DbUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class BorrowRecordDao {

    // Вспомогательный метод для маппинга
    private BorrowRecord extractRecordFromResultSet(ResultSet rs) throws SQLException {
        // Postgres хранит TIMESTAMP, который JDBC конвертирует в LocalDateTime
        return new BorrowRecord(
                rs.getInt("id"),
                rs.getInt("book_id"),
                rs.getString("borrower_name"),
                rs.getObject("borrow_date", LocalDateTime.class),
                rs.getObject("return_date", LocalDateTime.class)
        );
    }

    // 1. CREATE (Сохранение новой записи о выдаче)
    public BorrowRecord save(BorrowRecord record) {
        String sql = "INSERT INTO borrow_history (book_id, borrower_name, borrow_date) "
                + "VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, record.getBookId());
            stmt.setString(2, record.getBorrowerName());
            stmt.setObject(3, record.getBorrowDate()); // Используем setObject для LocalDateTime

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    record.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving borrow record: " + e.getMessage());
        }
        return record;
    }

    // 2. UPDATE (Отметка о возврате)
    public void markAsReturned(int bookId, LocalDateTime returnDate) {
        String sql = "UPDATE borrow_history SET return_date = ? WHERE book_id = ? AND return_date IS NULL";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, returnDate);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error marking record as returned: " + e.getMessage());
        }
    }

    // 3. READ (Получение активной записи о выдаче по ID книги)
    public Optional<BorrowRecord> findActiveRecordByBookId(int bookId) {
        String sql = "SELECT * FROM borrow_history WHERE book_id = ? AND return_date IS NULL";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractRecordFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding active record: " + e.getMessage());
        }
        return Optional.empty();
    }
}