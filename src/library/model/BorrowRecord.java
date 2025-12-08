package library.model;

import java.time.LocalDateTime;

public class BorrowRecord {
    private int id;
    private int bookId;
    private String borrowerName;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate; // null, если книга еще не возвращена

    // Конструктор для новой записи о выдаче
    public BorrowRecord(int bookId, String borrowerName, LocalDateTime borrowDate) {
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }

    // Конструктор для загрузки из БД
    public BorrowRecord(int id, int bookId, String borrowerName,
                        LocalDateTime borrowDate, LocalDateTime returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // --- Геттеры и Сеттеры ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBookId() { return bookId; }
    public String getBorrowerName() { return borrowerName; }
    public LocalDateTime getBorrowDate() { return borrowDate; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
}