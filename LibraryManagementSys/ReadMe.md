
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
PS:Constructor injection also enables testing

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

**Why Final matter?***
```java
private final BookRepository repository;
```
Once the service receives its dependency:Repository
It Never changes. This makes the class easier to reason about.
Immutable references reduce bugs. Note they are immuatable references and not immutable objects.
*final* is often taught as a keyword, but it's really a design tool.
Suppose you have this service:
```java
public class LibraryBookService {
    private BookRepository repository;
    public LibraryBookService(BookRepository repository) {
        this.repository = repository;
    }
}
```
Now imagine that 300 lines later in the same class, someone writes:
```java
repository = new FileBookRepository();
```
can we expect that? Probably not. Now imagine another method return by your collaborator:
```java
public void switchRepository() {
    repository = new RedisBookRepository();
}
```
Now ask yourself: Can I still trust what this service is talking to? 🤔
ofcourse no.
Now let's make it final
```java
private final BookRepository repository;
```
Now Java says:
"Once this variable has been assigned, it can never *point* to another object." (focus on the point)
So this is allowed:
```java
public LibraryBookService(BookRepository repository) {
    this.repository = repository;
}
```
But this is illegal:
```java
repository = new RedisBookRepository();
```
Compile-time error.
Emphasis on: *The reference cannot change.The object itself still can.*
Common Misconception is that final equals immutable. Thats not quite true though.
Suppose we have:
```java
private final List<String> books = new ArrayList<>();
```
we can do the below because reference is final. not the object.
This distinction is very important.

```java
books.add("Harry Potter");
books.remove(0);
```
Imagine your service has 20 methods without final: borrowBook(),returnBook(),search(),update(),delete() etc.Every one of those methods could potentially change the repository.
As a reviewer, one would have to inspect the entire class.
With final:
```java
private final BookRepository repository;
```
In a world of constant updates, this dependency is blissfully immutable.This dependency will never change.
That's less mental effort. reduced cognitive load 😮‍💨
This keeps the codebase clean, manageable, and easy to review.
Imagine you're debugging a production issue. You see:
```java
repository.save(book);
```
If repository is not final, you wonder: Was it reassigned? When?By whom? Under what conditions? You start searching everywhere. 
If it's final, you stop wondering. You know exactly where it came from.
**Constructor injection + final**
these two are usually paired. an excellent contract.
```java
public class LibraryBookService {

    private final BookRepository repository;

    public LibraryBookService(BookRepository repository) {
        this.repository = repository;
    }

}
```
This tells three things:
This dependency is required.
It must exist when the object is created.
It will never change.

Suppose you're working at a major bank handling millions or transactions. Your service processes millions of payments.
Imagine someone accidentally writes:
```java
paymentRepository = new DummyRepository();
```
One line. Production disaster.!!!💣
Using final prevents that category of mistake.

Suppose I open your class. The first thing I see:
```java
private final PaymentRepository repository;
private final NotificationService notificationService;
private final AuditService auditService;
```
Its understood, this service depends on
Repository
Notifications
Audit
And those dependencies are fixed.
This improves readability.
**Thread Safety**
Imagine two threads without final:
Thread A
```java
repository = repository2;
```
Thread B
```java
repository.save(...)
```
Now weird things can happen depending on timing. With final, the reference itself can't change after construction, which removes an entire class of concurrency problems.
***Important nuance: final alone does not make a class thread-safe. It simply ensures the reference is stable.***
**How would you decide?**
Category 1: Dependencies
```java
private final BookRepository repository;
private final EmailService emailService;
private final PaymentGateway paymentGateway;
```
These should almost always be final. Because the service always needs them.
```java
Category 2: Configuration
private final int maxRetries;
private final Duration timeout;
```
These are also great candidates for final.Configuration shouldn't randomly change.
```java
Category 3: State
private BookStatus status;
```
This is different. Status changes. It should not be final. Say you build a house the foundation is final but the furniture or wallpapers can change.
```java
public class OrderService {

    private OrderRepository repository;
    private EmailService emailService;
    private Order currentOrder;
    private int retryCount;
    private List<Order> processedOrders = new ArrayList<>();
}
```

1. OrderRepository
OrderRepository would be final ✅ 
The dependency of the service should never change after construction. The service always collaborates with exactly one repository implementation during its lifetime.

2. EmailService
EmailService should be final✅
This is another dependency. Dependencies are injected once and remain stable throughout the lifetime of the service.

3. currentOrder
should not be final ❌
This is state. State changes.

4. processedOrders
private List<Order> processedOrders = new ArrayList<>();
should be final✅
```java
private final List<Order> processedOrders =
        new ArrayList<>();
processedOrders.add(order);
processedOrders.remove(order);
processedOrders.clear();
```
We do not want the processedOrder overriden midway.
```java
processedOrders =
        new LinkedList<>();
```
The list itself changes. The reference doesn't have to.

5. retryCount
```java
private int retryCount;
```
❌ Not final.
Now imagine instead
```java
private final int maxRetries = 3;
```
This is different.Maximum retries is configuration.Configuration shouldn't change.So ✅ final.

**For objects, references matter. For primitive values, you're actually talking about the value itself.**
There is no separate object whose internal state changes like with a List.int is just the value.So final int means the value never changes.

**Why choose Enums?**
Boolean fields rarely scale well. Enums model the domain more explicitly and make future changes easier.