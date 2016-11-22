package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.services.ConnectionPropertyValues;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;


public abstract class AbstractDBHandler {
    protected static ConnectionPropertyValues configReader = new ConnectionPropertyValues();
    protected static HashMap DBprops = configReader.getPropValues();

    protected static final String DATABASE = "jdbc:postgresql://" + DBprops.get("url") + "/" + DBprops.get("database");
    protected static final String DB_USER = String.valueOf(DBprops.get("user"));
    protected static final String DB_PASSWORD = String.valueOf(DBprops.get("password"));

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

}
