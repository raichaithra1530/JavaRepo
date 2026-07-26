package library.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import library.model.Book;
import library.repository.BookRepository;

/*
* define services in library
* if the book is available, then we can issue it to the user
* if a book is returned, then we can update the availability of the book
* If the book is not available, then we can add the user to the waiting list
* If the book is returned, then we can notify the user in the waiting list
* and more as time goes by, we can add more services to the library
*/
public class LibraryBookService {
    private final BookRepository bookRepository;

    public LibraryBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public void addBook(Book book) {
        bookRepository.saveBook(book);
    }

    public void removeBook(String id) {
        bookRepository.deleteBookByID(id);
        
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.getByTitle(title);
    }
    
    public List<Book> searchByAuthor(String author) {
        return bookRepository.getByAuthor(author);
    }

    public List<Book> searchByGenre(String genre) {
        return bookRepository.getByGenre(genre);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAvailableBooks();
    }
    
    public boolean issueBook(String id) {
        Optional<Book> bookOpt = bookRepository.getBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.isAvailable()) {
                book.setAvailable(false);
                bookRepository.saveBook(book);
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(String id) {
        Optional<Book> bookOpt = bookRepository.getBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(true);
            bookRepository.saveBook(book);
            return true;
        }
        return false;
    }

    public List<Book> getAllAvailableBooks() {
        return bookRepository.getAvailableBooks().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }
}
