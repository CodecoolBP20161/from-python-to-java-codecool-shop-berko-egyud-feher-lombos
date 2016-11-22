package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Status;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDaoDB extends AbstractDBHandler implements OrderDao{

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
        String query = "DELETE FROM \"order\" WHERE id = '" + id +"';";
        executeQuery(query);
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM \"order\";";

        List<Order> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                Order order = new Order();
                resultList.add(order);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<Order> getBy(Status status) {
        return null;
    }

}
