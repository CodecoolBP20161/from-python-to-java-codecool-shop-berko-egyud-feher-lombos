package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javassist.NotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB extends AbstractDBHandler implements ProductCategoryDao {

    @Override
    public void add(ProductCategory category) {
        try {
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO \"category\" (\"NAME\", DESCRIPTION, DEPARTMENT) VALUES ( ?, ?, ?)");
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getDepartment());
            stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) throws NotFoundException {
        ProductCategory category;
        ProductDaoDB productDB = new ProductDaoDB();
        String query = "SELECT * FROM category WHERE ID = '" + id + "';";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)
        ){
            if (resultSet.next()) {
                category = new ProductCategory(resultSet.getInt("ID"),
                                      resultSet.getString("NAME"),
                                      resultSet.getString("DESCRIPTION"),
                                      resultSet.getString("DEPARTMENT"));
                productDB.getBy(category).forEach(category::addProduct);
                return category;
            } else {
                throw new NotFoundException("Product not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM \"category\" WHERE id = '" + id +"';";
        executeQuery(query);
    }

    @Override
    public List<ProductCategory> getAll() throws SQLException, NotFoundException {
        ProductCategory productCategory;
        ProductDaoDB productDB = new ProductDaoDB();
        List<ProductCategory> resultList = new ArrayList<>();
        String query = "SELECT * FROM category;";


        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)

        ){
            while (resultSet.next()) {
                productCategory = new ProductCategory(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("DESCRIPTION"),
                        resultSet.getString("DEPARTMENT"));
                productDB.getBy(productCategory).forEach(productCategory::addProduct);
                resultList.add(productCategory);
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
