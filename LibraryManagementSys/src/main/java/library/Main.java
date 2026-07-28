package library;

import java.util.List;
import java.util.Scanner;
import library.model.Book;
import library.repository.InMemoryBookDataStore;
import library.service.LibraryBookService;

public class Main {
    
      private static final LibraryBookService service = new LibraryBookService(new InMemoryBookDataStore());
      //Constructor Injection
      private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> searchByTitle();
                case 4 -> searchByAuthor();
                case 5 -> borrowBook();
                case 6 -> returnBook();
                case 7 -> listAllBooks();
                case 8 -> listAvailableBooks();
                case 9 -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid option.");
            }
      
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Search by Title");
        System.out.println("4. Search by Author");
        System.out.println("5. Borrow Book");
        System.out.println("6. Return Book");
        System.out.println("7. List All Books");
        System.out.println("8. List Available Books");
        System.out.println("9. Exit");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // ---- Action methods ----
    private static void addBook() {
        String id = readString("Enter ID: ");
        String title = readString("Enter Title: ");
        String author = readString("Enter Author: ");
        int year = readInt("Enter Publication Year: ");
        String genre = readString("Enter Genre: ");
        Book book = new Book(id, title, author, year, genre);
        service.addBook(book);
        System.out.println("Book added.");
    }

    private static void removeBook() {
        String id = readString("Enter book ID to remove: ");
        service.removeBook(id);
        System.out.println("Removed (if existed).");
    }

    private static void searchByTitle() {
        String title = readString("Enter title: ");
        List<Book> results = service.searchByTitle(title);
        printBooks(results);
    }

    private static void searchByAuthor() {
        String author = readString("Enter author: ");
        List<Book> results = service.searchByAuthor(author);
        printBooks(results);
    }

    private static void borrowBook() {
        String id = readString("Enter book ID to borrow: ");
        boolean success = service.issueBook(id);
        System.out.println(success ? "Book borrowed." : "Book not available or not found.");
    }

    private static void returnBook() {
        String id = readString("Enter book ID to return: ");
        boolean success = service.returnBook(id);
        System.out.println(success ? "Book returned." : "Book was not borrowed or not found.");
    }

    private static void listAllBooks() {
        printBooks(service.getAllBooks());
    }

    private static void listAvailableBooks() {
        printBooks(service.getAllAvailableBooks());
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            books.forEach(System.out::println);
        }
    
    }
    
}
