package factory.impl;

import dao.UserDAO;
import dao.impl.UserDaoJDBCImpl;
import factory.AbstractFactory;

public class AbstractFactoryImplJDBC implements AbstractFactory {
    public AbstractFactoryImplJDBC(){}
    public UserDAO createUserDao(){
        return UserDaoJDBCImpl.getInstance();
    }
}
