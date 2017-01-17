package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.ShippingDataDao;
import com.codecool.shop.model.Order;
import javassist.NotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class ShippingDataDB extends AbstractDBHandler implements ShippingDataDao {

    private static ShippingDataDB INSTANCE;

    public static ShippingDataDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShippingDataDB();
        }
        return INSTANCE;
    }

    private ShippingDataDB() {
    }

    @Override
    public void add(ArrayList<String> shippingDataList, Order order) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> find(int id) throws NotFoundException {
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
        }
        return null;
    }

}
