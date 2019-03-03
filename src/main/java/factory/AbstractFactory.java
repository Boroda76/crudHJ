package factory;

import dao.UserDAO;

public interface AbstractFactory {
   static AbstractFactory createFactory(){return null;}
   UserDAO createUserDao();
}
