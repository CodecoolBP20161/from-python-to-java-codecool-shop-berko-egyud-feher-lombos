package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoDB extends AbstractDBHandler implements ProductDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoDB.class);

    private static ProductDaoDB INSTANCE;

    public static ProductDaoDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductDaoDB();
        }
        return INSTANCE;
    }

    private ProductDaoDB() {
    }

    /**
     * Adds a product into the product database.
     * @param product
     */
    @Override
    public void add(Product product) {
        LOGGER.debug("add() method is called.");

        ProductCategoryDaoDB productDB = ProductCategoryDaoDB.getInstance();
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
            LOGGER.info("add() method insert productcategory name, description, and the department into ProductCategoryDB.");
            LOGGER.info("Product name: {}, description: {}, defaultPrice: {}, defaultCurrency: {}, productCategory: {}, Supplier: {}", product.getName(), product.getDescription(), product.getDefaultPrice(), product.getDefaultCurrency().toString(), product.getProductCategory().getId(), product.getSupplier().getId());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during order added into database: {}", e);
        }

    }

    /**
     * Finds a product with the given ID.
     * @param id
     * @return the product
     * @throws NotFoundException
     */
    @Override
    public Product find(int id) throws NotFoundException {
        LOGGER.debug("find() method is called.");

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
            LOGGER.error("Error occurred during order find(looked) in database: {}", e);
        }
        return null;
    }

    /**
     * Removes the product with the given ID.
     * @param id
     */
    @Override
    public void remove(int id) {
        LOGGER.debug("remove() method is called.");
        String query = "DELETE FROM product WHERE id = '" + id + "';";
        executeQuery(query);
    }

    /**
     * Collects all products into a list
     * @return all products
     * @throws NotFoundException
     */
    @Override
    public List<Product> getAll() throws NotFoundException {
        LOGGER.debug("getAll() method is called.");

        String query = "SELECT * FROM product;";
        return convertManyDBResultToObject(query);
    }

    /**
     * Collects the next 10 product from tha database
     * @param from
     * @return the next 10 product
     * @throws NotFoundException
     */
    public List<Product> getProductByPagination(Integer from) throws NotFoundException {
        LOGGER.debug("getProductByPagination() method is called.");
        String query = "SELECT * FROM product LIMIT 10 OFFSET " + from.toString() + ";";
        return convertManyDBResultToObject(query);
    }

    /**
     * Collects tha page numbers
     * @param allProduct
     * @return all page numbers
     * @throws NotFoundException
     */
    public List<Integer> getPageNumberList(Integer allProduct) throws NotFoundException {
        LOGGER.debug("getPageNumberList() method is called.");

        List<Integer> allProductList = new ArrayList<>();
        for (int i = 1; i < allProduct +1; i++) {
            allProductList.add(i);
        }
        return allProductList;
    }

    /**
     * Collects products from the given supplier
     * @param supplier
     * @return list of the products
     * @throws NotFoundException
     */
    @Override
    public List<Product> getBy(Supplier supplier) throws NotFoundException {
        LOGGER.debug("getBy() method is called (supplier)");

        String query = "SELECT * FROM product WHERE PRODUCT_SUPPLIER='" + supplier.getId() + "';";
        return convertManyDBResultToObject(query);
    }

    /**
     * Collects products from the given category
     * @param productCategory
     * @return list of the products
     * @throws NotFoundException
     */
    @Override
    public List<Product> getBy(ProductCategory productCategory) throws NotFoundException {
        LOGGER.debug("getBy() method is called (category)");

        String query = "SELECT * FROM product WHERE PRODUCT_CATEGORY='" + productCategory.getId() + "';";
        return convertManyDBResultToObject(query);
    }

    /**
     * Adds new product into the product database.
     * @param resultSet
     * @return new product
     * @throws SQLException
     * @throws NotFoundException
     */
    private Product createFromResultSet(ResultSet resultSet) throws SQLException, NotFoundException {
        LOGGER.debug("createFromResultSet method is called");

        ProductCategoryDaoDB categoryDB = ProductCategoryDaoDB.getInstance();
        SupplierDaoDB supplierDB = SupplierDaoDB.getInstance();

        return new Product(resultSet.getInt("ID"),
                resultSet.getString("NAME"),
                resultSet.getFloat("PRICE"),
                resultSet.getString("CURRENCY"),
                resultSet.getString("DESCRIPTION"),
                categoryDB.find(resultSet.getInt("PRODUCT_CATEGORY")),
                supplierDB.find(resultSet.getInt("PRODUCT_SUPPLIER")));
    }

    /**
     * Converts into a list of objects the previously selected products.
     * @param query
     * @return list of product objects
     * @throws NotFoundException
     */
    private ArrayList<Product> convertManyDBResultToObject(String query) throws NotFoundException {
        LOGGER.debug("convertManyDBResultToObject method is called.");

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
            LOGGER.error("Error occurred during convertManyDBResultToObject method called: {}", e);
        }
        return objectList;
    }

}