package library.repository;

import java.util.List;
import java.util.Optional;

import library.model.Book;

/*
    * 22July2026
    * This class is intended to manage the collection of books in the library.
    * It will provide methods to add, remove, and retrieve books, 
    *as well as to check their availability.
    * 
    * Lets keep the data structure in a map for faster lookups:
    * Map<String,Book> where the key is the book ID and the value is the Book object.
    * Also, data would be instore for now 
*/
public interface BookRepository {
    void saveBook(Book book);
    Optional<Book> getBookById(String id);
    List<Book> getByTitle(String title);
    List<Book> getByAuthor(String author);
    List<Book> getByGenre(String genre);
    void removeBook(String id);
    List<Book> getAvailableBooks();
}
