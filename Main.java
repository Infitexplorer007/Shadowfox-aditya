import java.util.Scanner;
import service.AuthService;
import service.LibraryService;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static AuthService authService = new AuthService();
    static LibraryService libraryService = new LibraryService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    static void register() {
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        authService.register(u, p);
    }

    static void login() {
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        if (authService.login(u, p)) {
            System.out.println("Login successful");
            userMenu();
        } else {
            System.out.println("Invalid credentials");
        }
    }

    static void userMenu() {
        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Logout");
            System.out.print("Choose: ");

            int c = sc.nextInt();

            switch (c) {
                case 1 -> libraryService.addBook();
                case 2 -> libraryService.viewBooks();
                case 3 -> libraryService.borrowBook();
                case 4 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }
}
