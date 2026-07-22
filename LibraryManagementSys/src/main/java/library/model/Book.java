package library.model;

public class Book {
    private String id;
    private String title;
    private String author;
    private int publicationYear;
    private String genre;
    private boolean isAvailable;

    public Book(String id, String title, String author, int publicationYear, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.isAvailable = true;//new books would be available by default initially
    }

    // Getters and setters (only include setter for available, others are immutable)
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    public String toString() {
        return String.format("Book[ID=%s, Title=%s, Author=%s, Year=%d, Genre=%s, Available=%b]",
                id, title, author, publicationYear, genre, isAvailable);
    }
}
