package util;

import config.DBHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    public static Properties readProperties(String fileName) {
        Properties properties = new Properties();

        try (InputStream is = DBHelper.class.getClassLoader().getResourceAsStream(fileName)) {

            properties.load(is);
        } catch (FileNotFoundException e) {
            System.out.println("Property file not found, please check path!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException while reading properties");
            e.printStackTrace();
        }
        return properties;
    }
}
