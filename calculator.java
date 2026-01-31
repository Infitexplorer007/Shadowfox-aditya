import java.util.Scanner;
public class Calculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose operation:");
            System.out.println("add | sub | mul | div | sqrt | pow | exit");
            System.out.print("Enter command: ");

            String choice = sc.next().toLowerCase();

            if (choice.equals("exit")) {
                System.out.println("Calculator closed.");
                break;
            }

            try {
                switch (choice) {
                    case "add":
                        System.out.print("Enter two numbers: ");
                        System.out.println("Result: " + (sc.nextDouble() + sc.nextDouble()));
                        break;

                    case "sub":
                        System.out.print("Enter two numbers: ");
                        System.out.println("Result: " + (sc.nextDouble() - sc.nextDouble()));
                        break;

                    case "mul":
                        System.out.print("Enter two numbers: ");
                        System.out.println("Result: " + (sc.nextDouble() * sc.nextDouble()));
                        break;

                    case "div":
                        System.out.print("Enter two numbers: ");
                        double b = sc.nextDouble();
                        if (b == 0) throw new ArithmeticException();
                        System.out.println("Result: " + (sc.nextDouble() / b));
                        break;

                    case "sqrt":
                        System.out.print("Enter number: ");
                        System.out.println("Result: " + Math.sqrt(sc.nextDouble()));
                        break;

                    case "pow":
                        System.out.print("Enter base and power: ");
                        System.out.println("Result: " +
                                Math.pow(sc.nextDouble(), sc.nextDouble()));
                        break;

                    default:
                        System.out.println("Invalid command!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                sc.nextLine();
            }
        }
        sc.close();
    }
}
