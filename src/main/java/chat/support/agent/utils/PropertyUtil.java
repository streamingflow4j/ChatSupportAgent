package chat.support.agent.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    public String getProperty(String property) {
        Properties properties = new Properties();
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(properties.getProperty("profiles.active").equals("dev")){
            return getPropertyDev(property);
        }else if (properties.getProperty("profiles.active").equals("prod")){
            return getPropertyProd(property);
        }
        return null;
    }

    public String getPropertyDev(String property) {
        Properties properties = new Properties();
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("application-dev.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(property);
    }

    public String getPropertyProd(String property) {
        Properties properties = new Properties();

        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("application-prod.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(property);
       // return environment.getRequiredProperty(property);
    }
}
