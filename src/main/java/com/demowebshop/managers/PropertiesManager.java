package com.demowebshop.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.junit.jupiter.api.Assertions;

/**
 * Класс работы со свойствами проета - загрузка из файла и их установка.
 */
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

    /**
     * Метод загрузки параметров работы тестов.
     * По умолчанию загружается файл "applications.properties", но если
     * в папке resources расположен файл с другим именем и его имя передано
     * системной переменной propFile - тогда будет загружен этот файл.
     * propFile - системная переменная для данного фреймворка и она не должна
     * совпадать по названию ни с какой системной переменной используемой ОС
     */
    private void loadApplicationProperites() {
        try {
            properties.load(new FileInputStream(new File(
                    "src/test/resources/" +
                            System.getProperty("propFile", "environment") +
                            ".properties")));
        } catch (IOException ex) {
            Assertions.fail("Конфигурационный файл 'environment.properties' не найден");
        } catch (IllegalArgumentException ex) {
            Assertions.fail("Конфигурационный файл поврежден");
        }
    }
    
    /**
     * Метод загрузки переменной из системных переменых, заданной вручную.
     * TODO Уточнить, каким путем это можно сделать
     */
    private void loadCustomProperites() {
        properties.forEach((key, value) -> System.getProperties()
        .forEach((customUserKey, customUserValue) -> {
        if(key.toString().equals(customUserKey.toString()) &&
                !value.toString().equals(customUserValue.toString())) {
                properties.setProperty(key.toString(), customUserValue.toString());
        }
        }));
    }
    
    /**
     * Получение значения системных переменных.
     * В случае отсутствия значения - возвращено указанное значение по умолчанию.
     * @param key ключ необходимого значения.
     * @param defaultValue значение по умолчанию.
     * @return значению переменной по ключу или дефолтное значение.
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Получение значени системной переменной.
     * @param key ключ необходимого значения.
     * @return значение соответсвующее ключу.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
