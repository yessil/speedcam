package speedcam;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    public static Properties loadProperties(String propFile){

        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream stream = loader.getResourceAsStream(propFile);
            props.load(stream);

        }catch (Exception ex){
            Logger.getLogger("util").log(Level.ALL, ex.getMessage());

        }
        return props;

    }
}
