package config;

import model.User;
import org.hibernate.cfg.Configuration;
import util.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBHelper {
    /**************************************
     *****************JDBC*****************
     **************************************/
    public static Connection getConnection() {
        return JDBCConnectionHolder.connection;
    }

    private static Connection createConnection() {
        Properties properties = PropertiesReader.readProperties("jdbc.properties");
        Connection connection = null;
        try {
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("login"), properties.getProperty("password"));
//            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Cannot connect with provided data");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load provided driver");
            e.printStackTrace();
        }
        return connection;
    }

    /**************************************
     ***************HIBERNATE**************
     **************************************/

    public static Configuration getConfiguration() {
        return HibernateConfigHolder.config;
    }

    private static Configuration createConfiguration() {
        Properties properties = PropertiesReader.readProperties("hibernate.properties");
        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    private static class JDBCConnectionHolder {
        private static final Connection connection = createConnection();
    }

    private static class HibernateConfigHolder {
        private final static Configuration config = createConfiguration();
    }
}
