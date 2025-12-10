package library.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int year;
    private Status status;

    public Book() {}

    public Book(int id, String title, String author, String genre, int year, Status status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.status = status;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
