package dao;

import java.sql.*;
import model.Book;
import dao.Database;

public class BookDAO {

    public void addBook(Book book) {
        String sql = "INSERT INTO books(title, author, isbn, available) VALUES(?,?,?,1)";

        try(Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }
    public void borrowBook(int bookId) {

    String sql = "UPDATE books SET available = 0 WHERE id = ? AND available = 1";

    try (Connection conn = Database.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, bookId);
        int rows = stmt.executeUpdate();

        if (rows == 0) {
            System.out.println("Book not available or invalid ID");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public void viewBooks() {
        String sql = "SELECT * FROM books";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("author") + " | " +
                    (rs.getInt("available") == 1 ? "Available" : "Borrowed")
                );
            }

        } catch (Exception e) {
            System.err.println("Error viewing books: " + e.getMessage());
        }
    }
}
