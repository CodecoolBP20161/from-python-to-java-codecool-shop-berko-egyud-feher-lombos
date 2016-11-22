package com.codecool.shop.testdata;


import com.codecool.shop.dao.implementation.db.ProductDaoDB;
import com.codecool.shop.dao.implementation.db.SupplierDaoDB;
import com.codecool.shop.model.Supplier;

public class TestDataDB {
    public static void main(String[] args) {
        SupplierDaoDB supDB = new SupplierDaoDB();

        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supDB.add(amazon);
        supDB.remove(1);
    }



}
