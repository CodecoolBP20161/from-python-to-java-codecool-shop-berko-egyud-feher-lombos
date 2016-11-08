import static spark.Spark.*;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        populateData();

        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());
        get("/hello", (req, res) -> "Hello World");
        get("/:id", ProductController::renderProducts, new ThymeleafTemplateEngine());

    }

    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier samsung = new Supplier("Samsung", "Digital content and services");
        supplierDataStore.add(samsung);
        Supplier lg = new Supplier("LG", "Digital content and services");
        supplierDataStore.add(lg);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory tv = new ProductCategory("TV", "Hardware", "TV is a telecommunication medium used for transmitting moving images in monochrome (black-and-white), or in color");
        productCategoryDataStore.add(tv);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Samsung 32J5200 LED", 350.9f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, samsung));
        productDataStore.add(new Product("LG 55EG910V OLED", 500.6f, "USD", "With ConnectShare Movie, simply plug your USB memory drive or HDD into the TV and instantaneously enjoy movies, photos or music.", tv, lg));

    }


}
