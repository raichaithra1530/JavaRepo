
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
The service does not create a repository. Someone using the service gives it one. Thats constructor injection. If we dont do this the service decides which repository to use. This achieves dependency inversion (SOLID). when the application needs something like a BookRepository the caller (here the main) decises if Postgres is to be used or may be Redis. The service has no idea what kind of repository would be used and hence this makes it loosely coupled and tommorow an infra change to use postgres or in memory or anything wouldnt matter much and would be easier to inculcate in the application.
More readability when you say **BookRepository**.
**Why not Setter Injection**
For example:
```java
LibraryBookService service =
        new LibraryBookService();

service.setRepository(repository);
```
What's wrong? What if someone forgets to assign a repository.
```java
LibraryBookService service =
        new LibraryBookService();

service.borrowBook(...);
```
💥 NullPointerException!!!
Constructor injection guarantees the object is created in a valid state.

**Why the Hashmap?**
Optimized Lookups🔎 with the keys. Duh!




