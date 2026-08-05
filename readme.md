**JAVA UNDERSTANDING**

**Is Java pass-by-reference?**
No.

**Is Java pass-by-value?**
Yes.

**Then why can methods modify objects?**
Because Java ***copies*** the reference value. Both variables point to the same object. The copy of the reference is passed. Analogy: Say the address to a house is written in a paper and the photocopy is passed to a caller but not the original paper. 
In Java the variable holds a reference to the object not the object itself.( A Placeholder may be)

***As per official Oracle Java documentation, Java is strictly pass-by-value, always. There is no pass-by-reference in Java.*** 
When Java passes it passes a copy of the reference value. Lets clarify this using an example

```java
public class Demo {

    public static void change(String text) {
        text = "Spring";
    }

    public static void main(String[] args) {

        String text = "Java";

        change(text);

        System.out.println(text);
    }
}
```

This prints : Java

if the code was 
```java
public class Demo {

    public static void change(String text) {
        text = "Spring";
        System.out.println(text);
    }

    public static void main(String[] args) {

        String text = "Java";

        change(text);

    }
}
```
It would have print : Spring

Because Java never passes the reference. Java passes a copy of the reference value.

Before calling change():
```java
main()

text
 |
 |
 +---------> "Java"
```

When we call
```java
change(text);
```
Java does this:
```java
main()

text
 |
 |
 +---------> "Java"


change()

text
 |
 |
 +---------> "Java"
```
There are two variables. Each contains the same reference value. Neither variable is shared. The value was copied. This is exactly what pass-by-value means.
Then this happens
```java
text = "Spring";
```
and then things change as:
```java
main()

text
 |
 |
 +---------> "Java"


change()

text
 |
 |
 +---------> "Spring"
```
Did we change the object? No.
We changed which object the local variable points to.This is why the caller is unaffected.

When the method finishes, text (the local variable) disappears. Now the "Spring" object has no references pointing to it. Eventually, the JVM ***may*** garbage collect it.
emphasis on may. Garbage collection is not immediate.
