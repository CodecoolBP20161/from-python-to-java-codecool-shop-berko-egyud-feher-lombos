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
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoDB.class);

    private static OrderDaoDB INSTANCE;

    public static OrderDaoDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderDaoDB();
        }
        return INSTANCE;
    }

    private OrderDaoDB() {
    }

    @Override
    public void add(Order order) {
        logger.info("add method is called.");
        String query = "INSERT INTO \"order\" (STATUS, TOTAL_PRICE, USER_ID) VALUES (?, ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, order.getStatus().toString());
            statement.setDouble(2, order.getTotalPrice());
            statement.setString(3, order.getUserSessionId());
            logger.info("Add method insert order status, totalPrice, and the UserSessionId into OrderDB.");
            logger.info("Order status: {}, totalPrice: {}, userSessionId: {}", order.getStatus().toString(), order.getTotalPrice(), order.getUserSessionId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                    logger.info("order get new id from database: {}", generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error occurred during order added into database: {}", e);
        }
    }

    @Override
    public Order find(int id) throws NotFoundException {
        logger.info("find method is called.");
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
            logger.error("Error occurred during order find(looked) in database: {}", e);
        }
        return null;
    }

    @Override
    public void remove(int id) {
        logger.info("remove method is called.");
        String query = "DELETE FROM \"order\" WHERE ID = '" + id + "';";
        executeQuery(query);
    }

    @Override
    public List<Order> getAll() throws NotFoundException {
        logger.info("getAll method is called.");

        String query = "SELECT * FROM \"order\";";
        return convertManyDBResultToObject(query);
    }

    @Override
    public List<Order> getBy(Status status) throws NotFoundException {
        logger.info("getBy method is called.");

        String query = "SELECT * FROM \"order\" WHERE STATUS='" + status.toString() + "';";
        return convertManyDBResultToObject(query);
    }

    private List<Order> convertManyDBResultToObject(String query) throws NotFoundException {
        logger.info("convertManyDBResultToObject method is called.");

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
            logger.error("Error occurred during convertManyDBResultToObject method called: {}", e);
        }
        return null;
    }

    private Order fillWithLineItem(Order order) throws NotFoundException {
        logger.info("fillWithLineItem method is called.");

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

    public void update(Order order){
        logger.info("update method is called.");

        String query = "UPDATE \"order\" SET TOTAL_PRICE = ?, STATUS=?";
        try {
            PreparedStatement stmt;
            stmt = connection.prepareStatement(query);
            stmt.setDouble(1, order.getTotalPrice());
            stmt.setString(2, order.getStatus().toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occurred during update method called: {}", e);
        }
    }
}
