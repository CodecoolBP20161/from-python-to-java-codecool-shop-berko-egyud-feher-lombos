package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.services.ConnectionPropertyValues;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ProductDaoDB extends AbstractDBHandler implements ProductDao {

    @Override
    public void add(Product product) {
        try {
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO \"product\" VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, Integer.toString(product.getId()));
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, (Float.toString(product.getDefaultPrice())));
            stmt.setString(5, product.getDefaultCurrency().toString());
            stmt.setString(6, Integer.toString(product.getProductCategory().getId()));
            stmt.setString(7, Integer.toString(product.getSupplier().getId()));
            stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
    }
}
