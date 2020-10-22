package ua.com.alevel.impl;


import ua.com.alevel.PropertiesReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReaderImpl implements PropertiesReader {
    @Override
    public Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream reader = PropertiesReaderImpl.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
