package service.impl;

import dao.UserDAO;
import exceptions.UserException;
import factory.AbstractFactory;
import model.User;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO dao;

    private UserServiceImpl() {
        this.dao = AbstractFactory.createUserDao();
    }

    public static UserService getInstance() {
        return UserServiceHolder.service;
    }

    @Override
    public void delete(Long id) throws UserException {
        dao.delete(id);
    }

    @Override
    public List<User> getAll() {
        return dao.getAll();
    }

    @Override
    public User getById(Long id) throws UserException {
        return dao.getById(id);
    }

    @Override
    public User getByLogin(String login) throws UserException {
        return dao.getByLogin(login);
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public void createUser(User user) throws UserException{
        dao.createUser(user);
    }

    private static class UserServiceHolder {
        private static final UserService service = new UserServiceImpl();
    }
}
