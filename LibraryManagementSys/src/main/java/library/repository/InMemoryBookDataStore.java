package library.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import library.model.Book;

public class InMemoryBookDataStore implements BookRepository {
  
    private final Map<String, Book> store = new HashMap<>();


    @Override
    public Optional<Book> getBookById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Book> getAvailableBooks() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteBookByID(String id) {
        store.remove(id);
    }

    @Override
    public List<Book> getByTitle(String title) {
        return store.values().stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getByAuthor(String author) {
        return store.values().stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getByGenre(String genre) {
        return store.values().stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }


    @Override
    public void saveBook(Book book) {
        store.put(book.getId(), book);
    }
}
