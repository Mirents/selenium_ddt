package com.dws.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.junit.jupiter.api.Assertions;

public class PropertiesManager {
    private final Properties properties = new Properties();
    private static PropertiesManager INSTANCE = null;
    
    private PropertiesManager() {
        loadApplicationProperites();
        loadCustomProperites();
    }
    
    public static PropertiesManager getThisProperties() {
        if(INSTANCE == null) {
            INSTANCE = new PropertiesManager();
        }
        return INSTANCE;
    }

    private void loadApplicationProperites() {
        try {
            properties.load(new FileInputStream(new File(
                    "src/test/resources/" +
                            System.getProperty("propFile", "application") +
                            ".properties")));
        } catch (IOException ex) {
            Assertions.fail("Configuration file 'application.properties' not found");
        } catch (IllegalArgumentException ex) {
            Assertions.fail("The config file is corrupted");
        }
    }
    
    private void loadCustomProperites() {
        properties.forEach((key, value) -> System.getProperties()
        .forEach((customUserKey, customUserValue) -> {
        if(key.toString().equals(customUserKey.toString()) &&
                !value.toString().equals(customUserValue.toString())) {
                properties.setProperty(key.toString(), customUserValue.toString());
        }
        }));
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
