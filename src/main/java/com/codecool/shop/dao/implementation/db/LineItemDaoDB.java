package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import javassist.NotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoDB extends AbstractDBHandler implements LineItemDao {

    @Override
    public void add(LineItem lineitem) {

        String query = "INSERT INTO lineitem (QUANTITY, PRODUCT, \"ORDER\") VALUES (?, ?, ?)");

        try (
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, lineitem.getQuantity());
            statement.setInt(2, lineitem.getProductId());
            statement.setInt(3, lineitem.getOrderId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println(generatedKeys.getInt(1));
                    lineitem.setId(generatedKeys.);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void remove(int id) {
        String query = "DELETE FROM lineitem WHERE id = '" + id + "';";
        executeQuery(query);
    }

    @Override
    public List<LineItem> getAll() throws NotFoundException {
        ProductDaoDB prodDB = new ProductDaoDB();
        String query = "SELECT * FROM lineitem;";
        List<LineItem> resultList = new ArrayList<>();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int parentProductId = resultSet.getInt("PRODUCT");
                int orderId = resultSet.getInt("ORDER");
                Product ParentProduct = prodDB.find(parentProductId);
                LineItem lineItem = new LineItem(ParentProduct, orderId);
                resultList.add(lineItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<LineItem> getBy(int orderId) throws NotFoundException {
        ProductDaoDB prodDB = new ProductDaoDB();
        String query = "SELECT * FROM lineitem WHERE \"ORDER\"='" + orderId + "';";
        List<LineItem> resultList = new ArrayList<>();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int parentProductId = resultSet.getInt("PROdUCT");
                Product ParentProduct = prodDB.find(parentProductId);
                LineItem lineItem = new LineItem(ParentProduct, orderId);
                resultList.add(lineItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
