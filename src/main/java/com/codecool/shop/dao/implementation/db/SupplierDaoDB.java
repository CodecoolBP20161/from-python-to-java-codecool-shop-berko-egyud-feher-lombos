package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.services.ConnectionPropertyValues;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class SupplierDaoDB implements SupplierDao {
    private static ConnectionPropertyValues configReader = new ConnectionPropertyValues();
    private static HashMap DBprops = configReader.getPropValues();

    private static final String DATABASE = "jdbc:postgresql://" + DBprops.get("url") + "/" + DBprops.get("database");
    private static final String DB_USER = String.valueOf(DBprops.get("user"));
    private static final String DB_PASSWORD = String.valueOf(DBprops.get("password"));

    @Override
    public void add(Supplier supplier) {
            try {
                PreparedStatement stmt;
                stmt = getConnection().prepareStatement("INSERT INTO \"supplier\" VALUES (?, ?, ?)");
                stmt.setString(1, Integer.toString(supplier.getId()));
                stmt.setString(2, supplier.getName());
                stmt.setString(3, supplier.getDescription());
                stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
        public Supplier find ( int id){
            return null;
        }

        @Override
        public void remove ( int id){

        }

        @Override
        public List<Supplier> getAll () {
            return null;
        }
        private Connection getConnection() throws SQLException {
            return DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
        }
}
