package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.services.ConnectionPropertyValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public abstract class AbstractDBHandler {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDBHandler.class);

    private static ConnectionPropertyValues configReader = new ConnectionPropertyValues();
    private static HashMap DBprops = configReader.getPropValuesOfDB();

    private static final String DATABASE = "jdbc:postgresql://" + DBprops.get("url") + "/" + DBprops.get("database");
    private static final String DB_USER = String.valueOf(DBprops.get("user"));
    private static final String DB_PASSWORD = String.valueOf(DBprops.get("password"));
    protected static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        logger.info("getConnection method is called.");

        if (connection == null) {
            logger.info("create connection");

            connection = DriverManager.getConnection(
                    DATABASE,
                    DB_USER,
                    DB_PASSWORD);
        }
        return connection;
        }

     void executeQuery(String query) {
        try (
             Statement statement = connection.createStatement()
        ){
            logger.info("Execute query on statement.");
            statement.execute(query);

        } catch (SQLException e) {
            logger.error("Error occurred during execute query on statement: {}", e);
            e.printStackTrace();
        }
    }

}
