package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Status;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoDB extends AbstractDBHandler implements OrderDao{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoDB.class);

    private static OrderDaoDB INSTANCE;

    public static OrderDaoDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderDaoDB();
        }
        return INSTANCE;
    }

    private OrderDaoDB() {
    }

    /**
     * Add new order into the database.
     * @param order
     */
    @Override
    public void add(Order order) {
        LOGGER.debug("add() method is called.");
        String query = "INSERT INTO \"order\" (STATUS, TOTAL_PRICE, USER_ID) VALUES (?, ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, order.getStatus().toString());
            statement.setDouble(2, order.getTotalPrice());
            statement.setString(3, order.getUserSessionId());
            LOGGER.info("add() method insert order status, totalPrice, and the UserSessionId into OrderDB.");
            LOGGER.info("Order status: {}, totalPrice: {}, userSessionId: {}", order.getStatus().toString(), order.getTotalPrice(), order.getUserSessionId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                    LOGGER.debug("order get new id from database: {}", generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during order added into database: {}", e);
        }
    }

    /**
     * Finds an order with the given id in the database.
     * @param id
     * @return null
     * @throws NotFoundException
     */
    @Override
    public Order find(int id) throws NotFoundException {
        LOGGER.debug("find() method is called.");
        String query = "SELECT * FROM \"order\" WHERE ID = '" + id + "';";
        try(
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)
        ){
            if (resultSet.next()) {

                return fillWithLineItem(
                        new Order(
                                Status.valueOf(resultSet.getString("STATUS")),
                                resultSet.getInt("ID")
                        )
                );

            } else {
                throw new NotFoundException("Order not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during order find(looked) in database: {}", e);
        }
        return null;
    }

    /**
     * Removes the order with the given id from the database.
     * @param id
     */
    @Override
    public void remove(int id) {
        LOGGER.debug("remove() method is called.");
        String query = "DELETE FROM \"order\" WHERE ID = '" + id + "';";
        executeQuery(query);
    }

    /**
     * Collects all orders into a list
     * @return all orders
     * @throws NotFoundException
     */
    @Override
    public List<Order> getAll() throws NotFoundException {
        LOGGER.debug("getAll() method is called.");

        String query = "SELECT * FROM \"order\";";
        return convertManyDBResultToObject(query);
    }

    /**
     * Collects orders with the given status into a list.
     * @param status
     * @return the selected orders
     * @throws NotFoundException
     */
    @Override
    public List<Order> getBy(Status status) throws NotFoundException {
        LOGGER.debug("getBy() method is called.");

        String query = "SELECT * FROM \"order\" WHERE STATUS='" + status.toString() + "';";
        return convertManyDBResultToObject(query);
    }

    /**
     * Converts into a list of objects the previously selected orders.
     * @param query
     * @return list of order objects or null
     * @throws NotFoundException
     */
    private List<Order> convertManyDBResultToObject(String query) throws NotFoundException {
        LOGGER.debug("convertManyDBResultToObject() method is called.");

        List<Order> objectList = new ArrayList<>();

        try (
             Statement statement =getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
                Order order = fillWithLineItem(
                        new Order(
                                Status.valueOf(resultSet.getString("STATUS")),
                                resultSet.getInt("ID")
                        )
                );
                objectList.add(order);
            }
            return objectList;

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during convertManyDBResultToObject() method called: {}", e);
        }
        return null;
    }

    /**
     * Puts the item into the selected order
     * @param order
     * @return
     * @throws NotFoundException
     */
    private Order fillWithLineItem(Order order) throws NotFoundException {
        LOGGER.debug("fillWithLineItem() method is called.");

        LineItemDaoDB lineItemDB = LineItemDaoDB.getInstance();
        ProductDaoDB productDB = ProductDaoDB.getInstance();
        List<LineItem> lineItems = lineItemDB.getBy(order.getId());

        // For each lineItem, add a product times the quantity, to the order
        for (LineItem lineItem : lineItems) {
            for(int i = 0; i < lineItem.getQuantity(); i++) {
                Product parentProduct = productDB.find(lineItem.getProductId());
                order.add(parentProduct);
            }
        }
        return order;
    }

    /**
     * Updates the status and the total price of the cart.
     * @param order
     */
    public void update(Order order){
        LOGGER.debug("update() method is called.");

        String query = "UPDATE \"order\" SET TOTAL_PRICE = ?, STATUS=?";
        try {
            PreparedStatement stmt;
            stmt = connection.prepareStatement(query);
            stmt.setDouble(1, order.getTotalPrice());
            stmt.setString(2, order.getStatus().toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during update() method called: {}", e);
        }
    }
}
