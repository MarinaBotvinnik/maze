package Server;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    public static String getProperty(String key) {

        try (InputStream input = Configurations.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            return prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
