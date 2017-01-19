package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoDB extends AbstractDBHandler implements LineItemDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(LineItemDaoDB.class);

    private static LineItemDaoDB INSTANCE;

    public static LineItemDaoDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LineItemDaoDB();
        }
        return INSTANCE;
    }

    private LineItemDaoDB() {
    }

    /**
     * Insert an item's quantity, productId, and the orderId of the user into LineItemDb.
     * @param lineitem - the item the user put in the cart
     */
    @Override
    public void add(LineItem lineitem) {
        LOGGER.debug("add(LineItem) method is called.");

        String query = "INSERT INTO lineitem (QUANTITY, PRODUCT, \"ORDER\") VALUES (?, ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, lineitem.getQuantity());
            statement.setInt(2, lineitem.getProductId());
            statement.setInt(3, lineitem.getOrderId());
            LOGGER.info("add() method insert lineitem quantity, productId, and the orderId into LineItemDb.");
            LOGGER.info("LineItem quantity: {}, product: {}, orderId: {}", lineitem.getQuantity(), lineitem.getProductId(), lineitem.getOrderId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lineitem.setId(generatedKeys.getInt(1));
                    LOGGER.debug("lineitem get new id from database: {}", generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during lineitem added into database: {}", e);
        }
    }


    /**
     * Remove an item from the cart.
     * @param id of the item
     */
    @Override
    public void remove(int id) {
        String query = "DELETE FROM lineitem WHERE id = '" + id + "';";
        executeQuery(query);
        LOGGER.info("remove method is called.");
    }

    /**
     * Collects all products in the cart to a list.
     * @return all items
     * @throws NotFoundException
     */
    @Override
    public List<LineItem> getAll() throws NotFoundException {
        LOGGER.debug("getAll() method is called, it gave the result it List.");

        ProductDaoDB prodDB = ProductDaoDB.getInstance();
        String query = "SELECT * FROM lineitem;";
        List<LineItem> resultList = new ArrayList<>();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int parentProductId = resultSet.getInt("PRODUCT");
                int orderId = resultSet.getInt("ORDER");
                Product ParentProduct = prodDB.find(parentProductId);
                LineItem lineItem = new LineItem(ParentProduct, orderId);
                resultList.add(lineItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during select all lineitem from database: {}", e);
        }
        return resultList;
    }

    /**
     * Collects all products of the given order.
     * @param orderId
     * @return all products of the order
     * @throws NotFoundException
     */
    @Override
    public List<LineItem> getBy(int orderId) throws NotFoundException {
        LOGGER.debug("getBy() method is called, it gave the result it List.");

        ProductDaoDB prodDB = ProductDaoDB.getInstance();
        String query = "SELECT * FROM lineitem WHERE \"ORDER\"='" + orderId + "';";
        List<LineItem> resultList = new ArrayList<>();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int parentProductId = resultSet.getInt("PROdUCT");
                Product ParentProduct = prodDB.find(parentProductId);
                LineItem lineItem = new LineItem(ParentProduct, orderId);
                resultList.add(lineItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during select lineitem filtered from database: {}", e);
        }
        return resultList;
    }
}
