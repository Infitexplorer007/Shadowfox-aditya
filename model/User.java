package model;

public class User {
    private int id;
    private final String username;
    private final String password;

    public int getId() {
        return id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
