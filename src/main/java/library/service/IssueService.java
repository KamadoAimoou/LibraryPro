package library.service;

import library.dao.IssueDAO;
import library.dao.impl.IssueDAOImpl;
import library.model.Book;
import library.model.IssuedBook;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class IssueService {

    private final IssueDAO issueDAO = new IssueDAOImpl();
    private final BookService bookService = new BookService();

    /**
     * Выдать книгу пользователю
     */
    public boolean issueBook(int userId, int bookId, LocalDate dueDate) {

        // Проверяем, существует ли книга
        Optional<Book> bookOpt = bookService.getById(bookId);
        if (bookOpt.isEmpty()) {
            return false;
        }

        Book book = bookOpt.get();

        // Проверяем, доступна ли книга
        if (!book.isAvailable()) {
            return false;
        }

        // Создаём запись о выдаче
        IssuedBook issued = new IssuedBook();
        issued.setBookId(bookId);
        issued.setUserId(userId);
        issued.setIssueDate(LocalDate.now());
        issued.setDueDate(dueDate);
        issued.setReturned(false);

        // Сохраняем в БД
        issueDAO.issueBook(issued);

        // Делаем книгу недоступной
        bookService.setAvailability(bookId, false);

        return true;
    }

    /**
     * Получить все активные (не возвращённые) выдачи
     */
    public List<IssuedBook> activeIssues() {
        return issueDAO.findActive();
    }

    /**
     * Вернуть книгу
     */
    public void returnBook(int issueId, int bookId) {
        issueDAO.markReturned(issueId);
        bookService.setAvailability(bookId, true);
    }
}