package dao;

import exceptions.UserException;
import model.User;

import java.util.List;

public interface UserDAO {
    //must be singleton!!!
    static UserDAO getInstance() {
        return null;
    }

    void delete(Long id) throws UserException;

    List<User> getAll();

    User getById(Long id) throws UserException;

    User getByLogin(String login) throws UserException;

    void updateUser(User user)  throws UserException;

    void createUser(User user) throws UserException;
}
