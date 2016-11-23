package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import javassist.NotFoundException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.sql.*;
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
    public void remove(ProductCategory category) {
        String query = "DELETE FROM \"category\" WHERE id = '" + category.getId() +"';";
        executeQuery(query);
    }

    @Override
    public List<ProductCategory> getAll() {throw new NotImplementedException();}

}
