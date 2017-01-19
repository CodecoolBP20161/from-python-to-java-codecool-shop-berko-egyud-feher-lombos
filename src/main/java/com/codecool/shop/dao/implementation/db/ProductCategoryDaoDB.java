package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB extends AbstractDBHandler implements ProductCategoryDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryDaoDB.class);

    private static ProductCategoryDaoDB INSTANCE;

    public static ProductCategoryDaoDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductCategoryDaoDB();
        }
        return INSTANCE;
    }

    private ProductCategoryDaoDB() {
    }

    /**
     * Adds new product category into the database.
     * @param category
     */
    @Override
    public void add(ProductCategory category) {
        LOGGER.debug("add() method is called.");

        try {
            PreparedStatement stmt;
            stmt = connection.prepareStatement("INSERT INTO \"category\" (NAME, DESCRIPTION, DEPARTMENT) VALUES ( ?, ?, ?)");
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getDepartment());
            stmt.executeUpdate();
            LOGGER.info("add() method insert productcategory name, description, and the department into ProductCategoryDB.");
            LOGGER.info("ProductCategory name: {}, description: {}, department: {}", category.getName(), category.getDescription(), category.getDepartment());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during order added into database: {}", e);
        }
    }

    /**
     * Finds the product category with the given ID.
     * @param id
     * @return the product category
     * @throws NotFoundException
     */
    @Override
    public ProductCategory find(int id) throws NotFoundException {
        LOGGER.debug("find() method is called.");
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
            LOGGER.error("Error occurred during order find(looked) in database: {}", e);
        }
        return null;
    }

    /**
     * Deletes the category with the given ID.
     * @param id
     */
    @Override
    public void remove(int id) {
        LOGGER.debug("remove() method is called.");
        String query = "DELETE FROM \"category\" WHERE id = '" + id +"';";
        executeQuery(query);
    }

    /**
     * Collects all of the product categories
     * @return list of the product categories
     * @throws SQLException
     * @throws NotFoundException
     */
    @Override
    public List<ProductCategory> getAll() throws SQLException, NotFoundException {
        LOGGER.debug("getAll() method is called.");
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
            LOGGER.error("Error occurred during getAll() method: ", e);
        }
        return null;
    }

    /**
     * Puts the proper products into the category
     * @param category
     * @return the category
     * @throws NotFoundException
     */
    public ProductCategory fillWithProducts(ProductCategory category) throws NotFoundException {
        LOGGER.debug("fillWithProducts() method is called.");

        ProductDaoDB productDB = ProductDaoDB.getInstance();
        // Iterating through the products queried by category, and adding them to the suppliers 'category' field
        productDB.getBy(category).forEach(category::addProduct);
        return category;
    }

}
