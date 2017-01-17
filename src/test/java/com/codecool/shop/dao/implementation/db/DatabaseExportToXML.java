package com.codecool.shop.dao.implementation.db;


import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseExportToXML {
    public static void createXMLFromDataBase() throws Exception
    {
        // database connection
        Class driverClass = Class.forName("org.postgresql.Driver");
        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "lombocska", "postgres");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("product");
        partialDataSet.addTable("supplier");
        partialDataSet.addTable("category");

        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/java/com/codecool/shop/dao/implementation/db/partial.xml"));

    }
}
