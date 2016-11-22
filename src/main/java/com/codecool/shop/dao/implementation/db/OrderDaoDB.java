package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Status;
import com.codecool.shop.services.ConnectionPropertyValues;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class OrderDaoDB implements OrderDao {
    private static ConnectionPropertyValues configReader = new ConnectionPropertyValues();
    private static HashMap DBprops = configReader.getPropValues();

    private static final String DATABASE = "jdbc:postgresql://" + DBprops.get("url") + "/" + DBprops.get("database");
    private static final String DB_USER = String.valueOf(DBprops.get("user"));
    private static final String DB_PASSWORD = String.valueOf(DBprops.get("password"));

    @Override
    public void add(Order order) {
        try {
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO \"order\" VALUES (?, ?, ?)");
            stmt.setString(1, Integer.toString(order.getId()));
            stmt.setString(2, order.getStatus().toString());
            stmt.setString(3, (Double.toString(order.getTotal())));
            stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public Order find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public List<Order> getBy(Status status) {
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
    }
}
