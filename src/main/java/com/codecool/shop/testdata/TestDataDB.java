package com.codecool.shop.testdata;


import com.codecool.shop.dao.implementation.db.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.db.ProductDaoDB;
import com.codecool.shop.dao.implementation.db.SupplierDaoDB;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.services.BuildTable;
import javassist.NotFoundException;

public class TestDataDB {
    public static void populateData() throws NotFoundException {
        ProductDaoDB productDB = ProductDaoDB.getInstance();
        ProductCategoryDaoDB CategoryDB = ProductCategoryDaoDB.getInstance();
        SupplierDaoDB supplierDB = new SupplierDaoDB();

        // Uncomment this if you don't want your data reset
        BuildTable.build();
        //________________________________________________

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDB.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDB.add(lenovo);
        Supplier samsung = new Supplier("Samsung", "Digital content and services");
        supplierDB.add(samsung);
        Supplier lg = new Supplier("LG", "Digital content and services");
        supplierDB.add(lg);

        // Re assigning suppliers to have the 'id' field from the DB, so we can assign them to a product
        amazon = supplierDB.find(1);
        lenovo = supplierDB.find(2);
        samsung = supplierDB.find(3);
        lg = supplierDB.find(4);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        CategoryDB.add(tablet);
        ProductCategory tv = new ProductCategory("TV", "Hardware", "TV is a telecommunication medium used for transmitting moving images in monochrome (black-and-white), or in color");
        CategoryDB.add(tv);

        // Re assigning categories to have the 'id' field from the DB, so we can assign them to a product
        tablet = CategoryDB.find(1);
        tv = CategoryDB.find(2);

        //setting up products and printing it
        productDB.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDB.add(new Product("Amazon Water", 57.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDB.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDB.add(new Product("Amazon Air", 70.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDB.add(new Product("Amazon Earth", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDB.add(new Product("Samsung 32J", 350.9f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, samsung));
        productDB.add(new Product("Samsung 43H", 460.9f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, samsung));
        productDB.add(new Product("Samsung 32J", 250.9f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, samsung));
        productDB.add(new Product("Samsung 50J", 620.9f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, samsung));
        productDB.add(new Product("Samsung 50J", 480.9f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, samsung));
        productDB.add(new Product("Samsung 55J", 450.9f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, samsung));
        productDB.add(new Product("LG 55EG910V", 500.6f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, lg));
        productDB.add(new Product("LG 32LF650V", 310.6f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, lg));
        productDB.add(new Product("LG 43LF632V", 400.6f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, lg));
        productDB.add(new Product("LG 40UF737V", 380.6f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, lg));
        productDB.add(new Product("LG 32LF4510", 290.6f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, lg));
        productDB.add(new Product("LG 55UF950V", 530.6f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, lg));
        productDB.add(new Product("Lenovo Tab", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDB.add(new Product("Lenovo i4", 642, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDB.add(new Product("Lenovo Padlet", 820, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDB.add(new Product("Lenovo IdeaPad", 210, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDB.add(new Product("Lenovo Pad", 320, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
    }
}
