package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.ShippingDataDao;
import com.codecool.shop.model.Order;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class ShippingDataDB extends AbstractDBHandler implements ShippingDataDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingDataDB.class);

    private static ShippingDataDB INSTANCE;

    public static ShippingDataDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShippingDataDB();
        }
        return INSTANCE;
    }

    private ShippingDataDB() {
    }

    /**
     * Adds shippinginformation and order information int the database
     * @param shippingDataList
     * @param order
     */
    @Override
    public void add(ArrayList<String> shippingDataList, Order order) {
        LOGGER.debug("add() method is called.");

        String query = "INSERT INTO shippingdata (FIRST_NAME, LAST_NAME, EMAIL, PHONE, ADRESS, CITY, STATE, ZIP_CODE, COMMENT, ORDER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, shippingDataList.get(0));
            statement.setString(2, shippingDataList.get(1));
            statement.setString(3, shippingDataList.get(2));
            statement.setString(4, shippingDataList.get(3));
            statement.setString(5, shippingDataList.get(4));
            statement.setString(6, shippingDataList.get(5));
            statement.setString(7, shippingDataList.get(6));
            statement.setInt(8, Integer.parseInt(shippingDataList.get(7)));
            statement.setString(9, shippingDataList.get(8));
            statement.setInt(10, order.getId());

            statement.executeUpdate();

            LOGGER.info("add() method insert shippingData FIRST_NAME, LAST_NAME, EMAIL, PHONE, ADRESS, CITY, STATE, ZIP_CODE, COMMENT, ORDER_ID into ShippingDataDB.");
            LOGGER.info("Order FIRST_NAME: {}, LAST_NAME: {}, EMAIL: {}, PHONE: {}, ADDRESS: {}, CITY: {}, STATE: {}, ZIP_CODE: {}, COMMENT: {}, ORDER_ID: {}, ",shippingDataList.get(0), shippingDataList.get(1), shippingDataList.get(2), shippingDataList.get(3), shippingDataList.get(4), shippingDataList.get(5), shippingDataList.get(6), shippingDataList.get(7), shippingDataList.get(8), order.getId());

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during shippingdata added into database: {}", e);
        }
    }

    /**
     * Collects shipping data with the given order ID
     * @param id
     * @return
     * @throws NotFoundException
     */
    public ArrayList<String> find(int id) throws NotFoundException {
        LOGGER.debug("find() method is called.");

        String query = "SELECT * FROM shippingdata WHERE ORDER_ID ='" + id + "';";
        ArrayList<String> userData;
        try(
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ){
            if (resultSet.next()) {
                String userEmail = resultSet.getString("EMAIL");
                String userFirstName = resultSet.getString("FIRST_NAME");
                String userLastName = resultSet.getString("LAST_NAME");
                String userCity = resultSet.getString("CITY");
                userData = new ArrayList<>(Arrays.asList(userEmail, userFirstName, userLastName, userCity));
                return userData;
            } else {
                throw new NotFoundException("Order not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error occurred during order find(looked) in database: {}", e);
        }
        return null;
    }

}
