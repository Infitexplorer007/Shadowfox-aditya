package service;

import dao.UserDAO;
import model.User;

public class AuthService {

    UserDAO userDAO = new UserDAO();

    public void register(String username, String password) {
        userDAO.register(new User(username, password));
    }

    public boolean login(String username, String password) {
        return userDAO.login(username, password);
    }
}
