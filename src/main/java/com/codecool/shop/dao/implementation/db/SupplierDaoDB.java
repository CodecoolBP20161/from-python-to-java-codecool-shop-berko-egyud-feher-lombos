package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.SupplierDao;
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

public class SupplierDaoDB extends AbstractDBHandler implements SupplierDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierDaoDB.class);


    private static SupplierDaoDB INSTANCE;

    public static SupplierDaoDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SupplierDaoDB();
        }
        return INSTANCE;
    }

    private SupplierDaoDB() {
    }

    /**
     * Adds a supplier into the supplier database
     * @param supplier
     */
    @Override
    public void add(Supplier supplier) {

        try {
            PreparedStatement stmt;
            stmt = getConnection().prepareStatement("INSERT INTO \"supplier\" (NAME, DESCRIPTION) VALUES (?, ?)");
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getDescription());
            stmt.executeUpdate();
            LOGGER.info("Add method insert order name and the description into SupplierDB.");
            LOGGER.info("Supplier name: {}, description: {}", supplier.getName(), supplier.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during order added into database: {}", e);
        }
    }

    /**
     * Finds a supplier with the given ID
     * @param id
     * @return the supplier
     * @throws NotFoundException
     */
    @Override
    public Supplier find ( int id) throws NotFoundException {
        LOGGER.debug("find() method is called.");
        Supplier supplier;
        String query = "SELECT * FROM supplier WHERE ID='" + id + "';";

        try (
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {

                ProductDaoDB productDB = ProductDaoDB.getInstance();
                supplier = new Supplier(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("DESCRIPTION"));

                return supplier;

            } else {
                throw new NotFoundException("Supplier not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during order find(looked) in database: {}", e);
        }
        return null;
    }

    /**
     * Removes the supplier with the given ID
     * @param id
     */
    @Override
    public void remove (int id){
        LOGGER.debug("remove() method is called.");
        String query = "DELETE FROM supplier WHERE id = '" + id +"';";
        executeQuery(query);
    }

    /**
     * Collects all suppliers
     * @return all suppliers
     * @throws NotFoundException
     */
    @Override
    public List<Supplier> getAll () throws NotFoundException {
        LOGGER.debug("getAll() method is called.");
        ProductDaoDB productDB = ProductDaoDB.getInstance();
        List<Supplier> resultList = new ArrayList<>();
        String query = "SELECT * FROM supplier;";


        try(
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)

        ){
            while (resultSet.next()) {

                Supplier supplier = new Supplier(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("DESCRIPTION"));

                fillWithProducts(supplier);
                resultList.add(supplier);
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during order find(looked) in database: {}", e);
        }
        return null;
    }

    /**
     * Puts the proper products into the supplier
     * @param supplier
     * @return the supplier
     * @throws NotFoundException
     */
    public Supplier fillWithProducts(Supplier supplier) throws NotFoundException {
        LOGGER.debug("fillWithProducts() method is called.");

        ProductDaoDB productDB = ProductDaoDB.getInstance();
        // Iterating through the products queried by category, and adding them to the suppliers 'category' field
        productDB.getBy(supplier).forEach(supplier::addProduct);
        return supplier;
    }
}
