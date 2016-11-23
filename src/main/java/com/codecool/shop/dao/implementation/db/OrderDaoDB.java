package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
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
        String query = "SELECT * FROM \"order\" WHERE ID = '" + id + "';";
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)
        ){
            if (resultSet.next()) {

                return fillWithLineItem(
                        new Order(
                                Status.valueOf(resultSet.getString("STATUS")),
                                resultSet.getInt("ID")
                        )
                );

            } else {
                throw new NotFoundException("Order not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM \"order\" WHERE ID = '" + id + "';";
        executeQuery(query);
    }

    @Override
    public List<Order> getAll() throws NotFoundException {
        String query = "SELECT * FROM \"order\";";
        return convertManyDBResultToObject(query);
    }

    @Override
    public List<Order> getBy(Status status) throws NotFoundException {
        String query = "SELECT * FROM \"order\" WHERE STATUS='" + status.toString() + "';";
        return convertManyDBResultToObject(query);
    }

    private List<Order> convertManyDBResultToObject(String query) throws NotFoundException {

        List<Order> objectList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
                Order order = fillWithLineItem(
                        new Order(
                                Status.valueOf(resultSet.getString("STATUS")),
                                resultSet.getInt("ID")
                        )
                );
                objectList.add(order);
            }
            return objectList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Order fillWithLineItem(Order order) throws NotFoundException {
        LineItemDaoDB lineItemDB = new LineItemDaoDB();
        ProductDaoDB productDB = new ProductDaoDB();
        List<LineItem> lineItems = lineItemDB.getBy(order.getId());

        // For each lineItem, add a product times the quantity, to the order
        for (LineItem lineItem : lineItems) {
            for(int i = 0; i < lineItem.getQuantity(); i++) {
                Product parentProduct = productDB.find(lineItem.getProductId());
                order.add(parentProduct);
            }
        }
        return order;
    };

}
