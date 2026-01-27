package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection connect() {
    try {
        return DriverManager.getConnection(URL);
    } 
    catch (java.sql.SQLException e) {
        // Printing the error message helps you know WHY it failed (e.g., file not found, driver missing)
        System.err.println("Database connection failed: " + e.getMessage());
        return null;
    }
    }
}
