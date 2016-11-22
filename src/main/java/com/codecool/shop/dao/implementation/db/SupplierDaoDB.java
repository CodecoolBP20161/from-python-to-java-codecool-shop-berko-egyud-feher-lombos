package com.codecool.shop.dao.implementation.db;


import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.services.ConnectionPropertyValues;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class SupplierDaoDB extends AbstractDBHandler implements SupplierDao {

    @Override
    public void add(Supplier supplier) {
            try {
                PreparedStatement stmt;
                stmt = getConnection().prepareStatement("INSERT INTO \"supplier\" VALUES (?, ?, ?)");
                stmt.setInt(1, supplier.getId());
                stmt.setString(2, supplier.getName());
                stmt.setString(3, supplier.getDescription());
                stmt.executeUpdate();
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
            String query = "DELETE FROM supplier WHERE id = '" + id +"';";
            executeQuery(query);
        }

        @Override
        public List<Supplier> getAll () {

            return null;
        }
}
