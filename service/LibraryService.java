package service;

import java.util.Scanner;
import dao.BookDAO;
import model.Book;

public class LibraryService {

    Scanner sc = new Scanner(System.in);
    BookDAO bookDAO = new BookDAO();

    public void addBook() {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();

        bookDAO.addBook(new Book(title, author, isbn));
        System.out.println("Book added");
    }

    public void viewBooks() {
        bookDAO.viewBooks();
    }

    public void borrowBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        bookDAO.borrowBook(id);
        System.out.println("Book borrowed");
    }
}
