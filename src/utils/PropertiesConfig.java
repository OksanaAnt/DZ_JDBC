package src.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertiesConfig implements IProperties {

    @Override
    public Map<String, String> read(){

        String rootFolder = System.getProperty("user.dir");
        Map<String, String> props = new HashMap<>();
        try (InputStream input = new FileInputStream(String.format("%s/src/resources/%s", rootFolder, "db.properties"))) {
            Properties properties = new Properties();
            properties.load(input);

            for(Map.Entry<Object, Object> entry: properties.entrySet()) {
                props.put(entry.getKey().toString(), entry.getValue().toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
