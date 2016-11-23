package com.codecool.shop.testdata;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.db.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.db.ProductDaoDB;
import com.codecool.shop.dao.implementation.db.SupplierDaoDB;
import com.codecool.shop.dao.implementation.mem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.mem.ProductDaoMem;
import com.codecool.shop.dao.implementation.mem.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

public class TestDataDB {
    public static void populateData() {
        ProductDaoDB productDB = new ProductDaoDB();
        ProductCategoryDaoDB CategoryDB = new ProductCategoryDaoDB();
        SupplierDaoDB supplierDB = new SupplierDaoDB();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDB.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDB.add(lenovo);
        Supplier samsung = new Supplier("Samsung", "Digital content and services");
        supplierDB.add(samsung);
        Supplier lg = new Supplier("LG", "Digital content and services");
        supplierDB.add(lg);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        CategoryDB.add(tablet);
        ProductCategory tv = new ProductCategory("TV", "Hardware", "TV is a telecommunication medium used for transmitting moving images in monochrome (black-and-white), or in color");
        CategoryDB.add(tv);

        //setting up products and printing it
        productDB.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDB.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDB.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDB.add(new Product("Samsung 32J5200 LED", 350.9f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, samsung));
        productDB.add(new Product("LG 55EG910V OLED", 500.6f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, lg));

    }
}
