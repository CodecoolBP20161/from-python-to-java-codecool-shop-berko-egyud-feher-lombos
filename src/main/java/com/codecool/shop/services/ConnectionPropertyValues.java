package com.codecool.shop.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class ConnectionPropertyValues {
    private HashMap<String, String> connectionProperties = new HashMap<>();
    private InputStream inputStream;

    // get the database connection properties from connection/connection.properties config file
    public HashMap<String, String> getPropValues() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "connection/connection.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            connectionProperties.put("url", prop.getProperty("url"));
            connectionProperties.put("database", prop.getProperty("database"));
            connectionProperties.put("user", prop.getProperty("user"));
            connectionProperties.put("password", prop.getProperty("password"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return connectionProperties;
    }

}
