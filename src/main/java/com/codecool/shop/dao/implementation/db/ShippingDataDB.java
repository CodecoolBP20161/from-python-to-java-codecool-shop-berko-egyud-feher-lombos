package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.ShippingDataDao;
import com.codecool.shop.model.Order;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShippingDataDB extends AbstractDBHandler implements ShippingDataDao {

    @Override
    public void add(ArrayList<String> shippingDataList, Order order) {

        String query = "INSERT INTO shippingdata (FIRST_NAME, LAST_NAME, EMAIL, PHONE, ADRESS, CITY, STATE, ZIP_CODE, COMMENT, \"ORDER\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
}
