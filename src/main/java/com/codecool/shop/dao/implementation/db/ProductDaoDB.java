package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import javassist.NotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoDB extends AbstractDBHandler implements ProductDao {

    @Override
    public void add(Product product) {
        ProductCategoryDaoDB productDB = new ProductCategoryDaoDB();
        try {
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO \"product\"(NAME, DESCRIPTION, PRICE, CURRENCY, PRODUCT_CATEGORY, PRODUCT_SUPPLIER) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setFloat(3, product.getDefaultPrice());
            stmt.setString(4, product.getDefaultCurrency().toString());
            stmt.setInt(5, product.getProductCategory().getId());
            stmt.setInt(6, product.getSupplier().getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product find(int id) throws NotFoundException {
        Product product;

        String query = "SELECT * FROM product WHERE ID = '" + id + "';";

        try (
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
    public List<Product> getAll() throws NotFoundException {
        String query = "SELECT * FROM product;";
        return convertManyDBResultToObject(query);
    }

    public List<Product> getProductByPagination(Integer from) throws NotFoundException {
        String query = "SELECT * FROM product LIMIT 10 OFFSET " + from.toString() + ";";
        return convertManyDBResultToObject(query);
    }

    @Override
    public List<Product> getBy(Supplier supplier) throws NotFoundException {
        String query = "SELECT * FROM product WHERE PRODUCT_SUPPLIER='" + supplier.getId() + "';";
        return convertManyDBResultToObject(query);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) throws NotFoundException {
        String query = "SELECT * FROM product WHERE PRODUCT_CATEGORY='" + productCategory.getId() + "';";
        return convertManyDBResultToObject(query);
    }

    private Product createFromResultSet(ResultSet resultSet) throws SQLException, NotFoundException {

        ProductCategoryDaoDB categoryDB = new ProductCategoryDaoDB();
        SupplierDaoDB supplierDB = new SupplierDaoDB();

        return new Product(resultSet.getInt("ID"),
                resultSet.getString("NAME"),
                resultSet.getFloat("PRICE"),
                resultSet.getString("CURRENCY"),
                resultSet.getString("DESCRIPTION"),
                categoryDB.find(resultSet.getInt("PRODUCT_CATEGORY")),
                supplierDB.find(resultSet.getInt("PRODUCT_SUPPLIER")));
    }
    private ArrayList<Product> convertManyDBResultToObject(String query) throws NotFoundException {
        ArrayList<Product> objectList = new ArrayList<>();
        Product product;
        try (
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                product = this.createFromResultSet(resultSet);
                objectList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectList;
    }

}