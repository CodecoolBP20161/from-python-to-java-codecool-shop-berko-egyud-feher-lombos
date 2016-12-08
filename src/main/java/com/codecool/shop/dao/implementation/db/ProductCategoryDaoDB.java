package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import javassist.NotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB extends AbstractDBHandler implements ProductCategoryDao {

    private static ProductCategoryDaoDB INSTANCE;

    public static ProductCategoryDaoDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductCategoryDaoDB();
        }
        return INSTANCE;
    }

    private ProductCategoryDaoDB() {
    }

    @Override
    public void add(ProductCategory category) {
        try {
            PreparedStatement stmt;
            stmt = connection.prepareStatement("INSERT INTO \"category\" (NAME, DESCRIPTION, DEPARTMENT) VALUES ( ?, ?, ?)");
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getDepartment());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) throws NotFoundException {
        ProductCategory category;

        String query = "SELECT * FROM category WHERE ID = '" + id + "';";

        try(
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)
        ){
            if (resultSet.next()) {
                category = new ProductCategory(resultSet.getInt("ID"),
                                      resultSet.getString("NAME"),
                                      resultSet.getString("DESCRIPTION"),
                                      resultSet.getString("DEPARTMENT"));
                return category;
            } else {
                throw new NotFoundException("Category not found");
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
        List<ProductCategory> resultList = new ArrayList<>();
        String query = "SELECT * FROM category;";


        try(
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)

        ){
            while (resultSet.next()) {
                productCategory = new ProductCategory(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("DESCRIPTION"),
                        resultSet.getString("DEPARTMENT"));
                productCategory = fillWithProducts(productCategory);
                resultList.add(productCategory);
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductCategory fillWithProducts(ProductCategory category) throws NotFoundException {
        ProductDaoDB productDB = new ProductDaoDB();
        // Iterating through the products queried by category, and adding them to the suppliers 'category' field
        productDB.getBy(category).forEach(category::addProduct);
        return category;
    }

}
