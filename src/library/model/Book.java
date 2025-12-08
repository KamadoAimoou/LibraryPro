package library.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int year;
    private Status status;

    // Конструктор для создания новой книги (ID и статус будут установлены DAO)
    public Book(String title, String author, String genre, int year) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.status = Status.AVAILABLE;
    }

    // Конструктор для загрузки из БД
    public Book(int id, String title, String author, String genre, int year, String statusDbValue) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.status = Status.fromDbValue(statusDbValue);
    }

    // --- Геттеры и Сеттеры ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}