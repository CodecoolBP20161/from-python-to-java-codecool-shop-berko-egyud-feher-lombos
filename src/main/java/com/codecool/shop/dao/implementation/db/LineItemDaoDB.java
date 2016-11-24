package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import javassist.NotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

 class LineItemDaoDB extends AbstractDBHandler implements LineItemDao {
    @Override
    public void add(LineItem lineitem) {
        try {
            PreparedStatement stmt;
            stmt = connection.prepareStatement("INSERT INTO lineitem (QUANTITY, PROdUCT, \"ORDER\") VALUES (?, ?, ?)");
            stmt.setInt(1, lineitem.getQuantity());
            stmt.setInt(2, lineitem.getProductId());
            stmt.setInt(3, lineitem.getOrderId());
            stmt.executeUpdate();
        } catch (Exception e) {
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
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
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
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
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
