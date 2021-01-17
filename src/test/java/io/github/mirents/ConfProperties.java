// Класс для чтения настроек из файлов "*.properties"

package io.github.mirents;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfProperties {
    private FileInputStream fileInputStream;
    private Properties property;
        
    ConfProperties(String file) {
        try {
            //указание пути до файла с настройками
            fileInputStream = new FileInputStream(file);
            property = new Properties();
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace(); }
        }
    }
    public String getProperty(String key) {
        return property.getProperty(key);
    }
}
