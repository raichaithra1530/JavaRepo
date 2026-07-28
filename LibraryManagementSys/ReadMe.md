
📚Library Management System

> [!NOTE]
**How to run?**
```bash
cd ..
mkdir LibraryManagementSys
cd LibraryManagementSys
mkdir -p src/main/java/library/model
mkdir -p src/main/java/library/service
mkdir -p src/main/java/library/repository
mkdir -p src/main/java/library/exception
mkdir -p src/main/java/library/ui
javac -d out $(find src -name "*.java")
java -cp out library.Main

```
> # **Initial Notes**
> [!NOTE]
**Why interface BookRepository**
We are implementing interface to introduce abstraction( OOP Concept) hence seperating and leaving out the code for future enhancements. Separation of Concerns (SoC) design principle (goal) achieved 🎉

**Why Constructor Injection?**
```java
private static final LibraryBookService service = new LibraryBookService(new InMemoryBookDataStore());
```
Suppose you have:

```java
public class LibraryBookService {

    private final BookRepository repository;

    public LibraryBookService(BookRepository repository) {
        this.repository = repository;
    }
}
```
**Why the Hashmap?**
Optimized Lookups🔎 with the keys. Duh!




