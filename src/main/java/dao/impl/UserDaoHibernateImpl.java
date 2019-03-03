package dao.impl;

import config.DBHelper;
import dao.UserDAO;
import exceptions.UserException;
import model.User;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

@SuppressWarnings("all")
public class UserDaoHibernateImpl implements UserDAO {
    private final SessionFactory factory;
    private Session session;

    private UserDaoHibernateImpl() {
        Configuration configuration = DBHelper.getConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry registry = builder.build();
        factory = configuration.buildSessionFactory();
    }

    //session factory initialization

    public static UserDaoHibernateImpl getInstance() {
        return UserDAOHolder.dao;
    }

    @Override
    public void delete(Long id) throws UserException {
        Transaction tx;
        session = factory.openSession();
        Query query = session.createQuery("delete User where id=:id");
        query.setParameter("id", id);
        tx = session.beginTransaction();
        int result = query.executeUpdate();

        session.flush();
        tx.commit();
        session.close();
        if (result != 1) throw new UserException("No user with provided ID found");
    }

    @Override
    public List<User> getAll() {
        session = factory.openSession();
        List<User> users;
        Criteria criteria = session.createCriteria(User.class);
        users = criteria.list();
        session.close();
        return users;
    }

    @Override
    public User getByLogin(String login) throws UserException {
        session = factory.openSession();
        User user;
        Query query=session.createQuery("from User where login=:login");
        query.setParameter("login", login);
        user = (User) query.uniqueResult();
        session.close();
        if (user == null) throw new UserException("No user with provided LOGIN found");
        return user;
    }

    @Override
    public User getById(Long id) throws UserException {
        session = factory.openSession();
        User user;
        user = (User) session.get(User.class, id);
        session.close();
        if (user == null) throw new UserException("No user with provided ID found");
        return user;
    }

    @Override
    public void updateUser(User user) {
        Transaction tx;
        session = factory.openSession();
        session.replicate(user, ReplicationMode.OVERWRITE);
        tx = session.beginTransaction();
        session.flush();
        tx.commit();
        session.close();
    }

    @Override
    public void createUser(User user) throws UserException{
        try {
            Transaction tx;
            session = factory.openSession();
            session.save(user);
            tx = session.beginTransaction();
            session.flush();
            tx.commit();
        }catch (Exception e){
            throw new UserException(e.getMessage());
        } finally {
            session.close();
        }

    }

    private static class UserDAOHolder {
        private static final UserDaoHibernateImpl dao = new UserDaoHibernateImpl();
    }
}
