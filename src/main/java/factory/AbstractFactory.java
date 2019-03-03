package factory;

import dao.UserDAO;
import factory.impl.AbstractFactoryImplHibernate;
import factory.impl.AbstractFactoryImplJDBC;
import util.PropertiesReader;

import java.util.Properties;

public interface AbstractFactory {
    Properties properties = PropertiesReader.readProperties("factory.properties");

    static UserDAO createUserDao() {
        switch (properties.getProperty("version")) {
            case "jdbc":
                return new AbstractFactoryImplJDBC().createUserDao();
            case "hibernate":
                return new AbstractFactoryImplHibernate().createUserDao();
            default:
                return null;
        }
    }
}
