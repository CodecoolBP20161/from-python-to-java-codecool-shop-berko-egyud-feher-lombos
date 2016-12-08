package com.codecool.shop.dao.implementation.db;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import javassist.NotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoDB extends AbstractDBHandler implements SupplierDao {

    @Override
    public void add(Supplier supplier) {
            try {
                PreparedStatement stmt;
                stmt = getConnection().prepareStatement("INSERT INTO \"supplier\" (NAME, DESCRIPTION) VALUES (?, ?)");
                stmt.setString(1, supplier.getName());
                stmt.setString(2, supplier.getDescription());
                stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
        public Supplier find ( int id) throws NotFoundException {
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
            }
            return null;
        }

        @Override
        public void remove (int id){
            String query = "DELETE FROM supplier WHERE id = '" + id +"';";
            executeQuery(query);
        }

        @Override
        public List<Supplier> getAll () throws NotFoundException {
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
            }
            return null;
        }

        public Supplier fillWithProducts(Supplier supplier) throws NotFoundException {
            ProductDaoDB productDB = ProductDaoDB.getInstance();
            // Iterating through the products queried by category, and adding them to the suppliers 'category' field
            productDB.getBy(supplier).forEach(supplier::addProduct);
            return supplier;
        }
}
