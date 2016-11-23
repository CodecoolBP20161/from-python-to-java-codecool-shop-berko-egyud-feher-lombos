package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.*;
import com.codecool.shop.services.ConnectionPropertyValues;
import javassist.NotFoundException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDaoDB extends AbstractDBHandler implements ProductDao {

    @Override
    public void add(Product product) {
        try {
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO \"product\"(\"NAME\", DESCRIPTION, PRICE, CURRENCY, PRODUCT_CATEGORY, PRODUCT_SUPPLIER) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setFloat(3, product.getDefaultPrice());
            stmt.setString(4, product.getDefaultCurrency().toString());
            stmt.setInt(5, product.getProductCategory().getId());
            stmt.setInt(6, product.getSupplier().getId());
            stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product find(int id) throws NotFoundException {
        Product product;

        String query = "SELECT * FROM product WHERE ID = '" + id + "';";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                product = this.createFromResultSet(resultSet);
                return product;
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
        String query = "DELETE FROM product WHERE id = '" + id + "';";
        executeQuery(query);
    }

    @Override
    public List<Product> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Product> getBy(Supplier supplier) throws NotFoundException {
        Product product;
        List<Product> resultList = new ArrayList<>();

        String query = "SELECT * FROM product WHERE PRODUCT_SUPPLIER='" + supplier.getId() + "';";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                product = this.createFromResultSet(resultSet);
                resultList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) throws NotFoundException {
        Product product;
        List<Product> resultList = new ArrayList<>();

        String query = "SELECT * FROM product WHERE PRODUCT_CATEGORY='" + productCategory.getId() + "';";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                product = this.createFromResultSet(resultSet);
                resultList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public Product createFromResultSet(ResultSet resultSet) throws SQLException, NotFoundException {

        ProductCategoryDaoDB categoryDB = new ProductCategoryDaoDB();
        SupplierDaoDB supplierDB = new SupplierDaoDB();

        return new Product(resultSet.getInt("ID"),
                resultSet.getString("NAME"),
                resultSet.getFloat("PRICE"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getString("CURRENCY"),
                categoryDB.find(resultSet.getInt("PRODUCT_CATEGORY")),
                supplierDB.find(resultSet.getInt("PRODUCT_SUPPLIER")));
    }
}
