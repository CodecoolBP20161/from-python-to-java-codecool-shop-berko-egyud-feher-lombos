package com.codecool.shop.services;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class BuildTable {
    public static void build() {
         ConnectionPropertyValues configReader = new ConnectionPropertyValues();
         HashMap DBprops = configReader.getPropValuesOfDB();

        String DATABASE = "jdbc:postgresql://" + DBprops.get("url") + "/" + DBprops.get("database");
         String DB_USER = String.valueOf(DBprops.get("user"));
         String DB_PASSWORD = String.valueOf(DBprops.get("password"));

        try (Connection connection = DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD)){
            Statement statement = connection.createStatement();

            String createProduct =  "DROP TABLE IF EXISTS  product CASCADE;" +
                                    "CREATE TABLE product" +
                                    "(" +
                                    "ID SERIAL PRIMARY KEY," +
                                    "NAME varchar(255)," +
                                    "DESCRIPTION varchar(255)," +
                                    "PRICE varchar(255)," +
                                    "CURRENCY varchar(255)," +
                                    "PRODUCT_CATEGORY int references category(ID) ON DELETE CASCADE," +
                                    "PRODUCT_SUPPLIER int references supplier(ID) ON DELETE CASCADE" +
                                    ");";

            String createCategory = "DROP TABLE IF EXISTS category CASCADE;" +
                                    "CREATE TABLE category" +
                                    "(" +
                                    "ID SERIAL PRIMARY KEY," +
                                    "NAME varchar(255)," +
                                    "DESCRIPTION varchar(255)," +
                                    "DEPARTMENT varchar(255)" +
                                    ");";

            String createOrder = "DROP TABLE IF EXISTS \"order\" CASCADE;" +
                                 "CREATE TABLE \"order\"" +
                                 "(" +
                                 "ID SERIAL PRIMARY KEY," +
                                 "STATUS varchar(255)," +
                                 "TOTAL_PRICE varchar(255)," +
                                 "USER_ID varchar(255)" +
                                 ");";

            String createSupplier = "DROP TABLE IF EXISTS supplier CASCADE;" +
                                    "CREATE TABLE supplier" +
                                    "(" +
                                    "ID SERIAL PRIMARY KEY," +
                                    "NAME varchar(255)," +
                                    "DESCRIPTION varchar(255)" +
                                    ");";

            String createLineItem = "DROP TABLE IF EXISTS lineitem CASCADE;" +
                                    "CREATE TABLE lineitem" +
                                    "(" +
                                    "ID SERIAL PRIMARY KEY," +
                                    "QUANTITY int," +
                                    "PRODUCT int REFERENCES product(ID) ON DELETE CASCADE," +
                                    "\"ORDER\" int REFERENCES \"order\"(ID) ON DELETE CASCADE" +
                                    ");";

            String createLoginTable = "DROP TABLE IF EXISTS logintable CASCADE;" +
                                      "CREATE TABLE logintable" +
                                      "(" +
                                      "ID SERIAL PRIMARY KEY," +
                                      "USERNAME varchar(255) UNIQUE," +
                                      "EMAIL VARCHAR(255)," +
                                      "PASSWORD varchar(255) NOT NULL UNIQUE," +
                                      "SALT  varchar(255) NOT NULL UNIQUE" +
                                      ");";


            String createShippingData = "DROP TABLE IF EXISTS shippingdata CASCADE;" +
                    "CREATE TABLE shippingdata" +
                    "(" +
                    "ID SERIAL PRIMARY KEY," +
                    "FIRST_NAME varchar(255)," +
                    "LAST_NAME varchar(255)," +
                    "EMAIL varchar(255)," +
                    "PHONE varchar(255)," +
                    "ADRESS varchar(255)," +
                    "CITY varchar(255)," +
                    "STATE varchar(255)," +
                    "ZIP_CODE int," +
                    "COMMENT varchar(255)," +
                    "ORDER_ID int REFERENCES \"order\"(ID) ON DELETE CASCADE" +
                    ");";


        statement.execute(createSupplier + createCategory + createOrder + createProduct + createLineItem + createShippingData + createLoginTable);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
