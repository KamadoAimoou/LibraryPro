package library.dao;

import library.model.Book;
import library.model.BorrowRecord;
import library.model.Status;
import library.util.DbUtil;

import java.time.LocalDateTime;
import java.util.List;

public class DbTestRunner {
    public static void main(String[] args) {
        try {
            // 1. Инициализация БД
            System.out.println("--- 1. Инициализация БД (проверка соединения) ---");
            DbUtil.initializeDatabase();

            BookDao bookDao = new BookDao();
            BorrowRecordDao recordDao = new BorrowRecordDao();

            // 2. Добавление тестовых данных
            System.out.println("--- 2. Добавление тестовых книг ---");
            Book book1 = new Book("Фауст", "Иоганн Гёте", "Классика", 1808);
            bookDao.save(book1);

            Book book2 = new Book("Сто лет одиночества", "Габриэль Гарсиа Маркес", "Магический реализм", 1967);
            bookDao.save(book2);

            System.out.println("Книги сохранены. ID: " + book1.getId() + ", " + book2.getId());

            // 3. Тест выдачи
            System.out.println("--- 3. Тест выдачи книги (Borrow) ---");
            BorrowRecord record = new BorrowRecord(book1.getId(), "Аймоу", LocalDateTime.now());
            recordDao.save(record);
            bookDao.updateStatus(book1.getId(), Status.BORROWED);
            System.out.println("Книга '" + book1.getTitle() + "' выдана.");

            // 4. Проверка всех данных
            System.out.println("--- 4. Проверка метода getAll() ---");
            List<Book> books = bookDao.getAll();
            books.forEach(b ->
                    System.out.println("-> ID " + b.getId() + ": " + b.getTitle() + " | Статус: " + b.getStatus())
            );

        } catch (Exception e) {
            System.err.println("!!! ОШИБКА ПРИ ВЫПОЛНЕНИИ ТЕСТА !!! Проверьте PgAdmin4 и пароль в DbUtil.");
            e.printStackTrace();
        }
    }
}