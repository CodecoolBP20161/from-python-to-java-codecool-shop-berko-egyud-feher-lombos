package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Status;
import javassist.NotFoundException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDaoDB extends AbstractDBHandler implements OrderDao{

    @Override
    public void add(Order order) {
        try {
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO \"order\" (STATUS, TOTAL_PRICE) VALUES (?, ?)");
            stmt.setString(1, order.getStatus().toString());
            stmt.setDouble(2, order.getTotalPrice());
            stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public Order find(int id) throws NotFoundException {
        Order order;
        LineItemDaoDB lineItemDB = new LineItemDaoDB();
        ProductDaoDB productDB = new ProductDaoDB();
        String query = "SELECT * FROM \"order\" WHERE ID = '" + id + "';";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
        ){
            if (resultSet.next()) {

                order = new Order(Status.valueOf(resultSet.getString("STATUS")), resultSet.getInt("id"));
                List<LineItem> lineItems = lineItemDB.getBy(order.getId());
                // For each lineItem, add a product times the quantity, to the order.
                // Maybe we should implement a method in order which adds lineItem, and not product, we should ask Thommyboy
                for (LineItem lineItem : lineItems) {
                    for(int quantity = 0; quantity < lineItem.getQuantity(); quantity++) {
                        order.add(productDB.find(lineItem.getProductId()));
                    }
                }
                return order;

            } else {
                throw new NotFoundException("Order not found");
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Order order) {
        String query = "DELETE FROM \"order\" WHERE ID = '" + order.getId() +"';";
        executeQuery(query);
    }

    @Override
    public List<Order> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Order> getBy(Status status) throws NotFoundException {
        LineItemDaoDB lineItemDB = new LineItemDaoDB();
        ProductDaoDB productDB = new ProductDaoDB();
        String query = "SELECT * FROM \"order\" WHERE STATUS='" + status.toString() + "';";
        List<Order> resultList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
                Order order = new Order(Status.valueOf(resultSet.getString("STATUS")), resultSet.getInt("id"));
                List<LineItem> lineItems = lineItemDB.getBy(order.getId());
                // For each lineItem, add a product times the quantity, to the order.
                for (LineItem lineItem : lineItems) {
                    for(int quantity = 0; quantity < lineItem.getQuantity(); quantity++) {
                        order.add(productDB.find(lineItem.getProductId()));
                    }
                }
                resultList.add(order);
            }
            return resultList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
