package service.impl;

import dao.UserDAO;
import dao.impl.UserDaoHibernateImpl;
import exceptions.UserException;
import factory.AbstractFactory;
import factory.AbstractFactoryCfg;
import model.User;
import service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserDAO dao;

    private UserServiceImpl() {
        Optional<AbstractFactory> factory=AbstractFactoryCfg.createFactory();
        //if not specified factory property set default hibernate realisation
        this.dao = factory.map(AbstractFactory::createUserDao).orElse(UserDaoHibernateImpl.getInstance());
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
    public void updateUser(User user) throws UserException{
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
