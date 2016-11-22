package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.services.ConnectionPropertyValues;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ProductCategoryDaoDB extends AbstractDBHandler implements ProductCategoryDao {

    @Override
    public void add(ProductCategory category) {
        try {
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO \"category\" VALUES (?, ?, ?, ?)");
            stmt.setString(1, Integer.toString(category.getId()));
            stmt.setString(2, category.getName());
            stmt.setString(3, category.getDescription());
            stmt.setString(4, category.getDepartment());
            stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }

}
