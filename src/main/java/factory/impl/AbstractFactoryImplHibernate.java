package factory.impl;

import dao.UserDAO;
import dao.impl.UserDaoHibernateImpl;
import factory.AbstractFactory;

public class AbstractFactoryImplHibernate implements AbstractFactory {
    public UserDAO createUserDao(){
        return UserDaoHibernateImpl.getInstance();
    }
}
