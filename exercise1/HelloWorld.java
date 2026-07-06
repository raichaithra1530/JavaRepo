// HelloWorld.java
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Hello! What is your name?");
        String name = scanner.nextLine();
        
        System.out.println("Nice to meet you, " + name + "!");
        System.out.println("How old are you?");
        int age = scanner.nextInt();
        
        System.out.println("You are " + age + " years old. Next year you will be " + (age + 1) + ".");
        
        scanner.close();
    }
}