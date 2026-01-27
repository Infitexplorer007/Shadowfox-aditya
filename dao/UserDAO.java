package dao;
import java.sql.*;
import model.User;
import dao.Database;

public class UserDAO {

    public void register(User user) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?,?,?)";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, "USER");
            stmt.executeUpdate();

            System.out.println("User registered");

        } catch (Exception e) {
            System.out.println("Username already exists");
        }
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            return false;
        }
    }
}
