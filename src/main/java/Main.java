import com.codecool.shop.controller.Controller;
import com.codecool.shop.controller.OrderControllerDB;
import com.codecool.shop.controller.ProductControllerDB;
import com.codecool.shop.dao.implementation.db.AbstractDBHandler;
import com.codecool.shop.testdata.TestDataDB;
import javassist.NotFoundException;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) throws NotFoundException {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        TestDataDB.populateData();

        before((request, response) -> AbstractDBHandler.getConnection());

        get("/add/:id", Controller::addToCart);
        get("/remove/:id", Controller :: removeFromCart);
        get("/removeall", Controller :: removeAllFromCart);

        get("/", ProductControllerDB::renderProducts, new ThymeleafTemplateEngine());
        get("/hello", (req, res) -> "Hello World");
        get("/category/:categoryid", ProductControllerDB::renderProducts, new ThymeleafTemplateEngine());
        get("/supplier/:supplierid", ProductControllerDB::renderProducts, new ThymeleafTemplateEngine());
        get("/cartcontent", OrderControllerDB::renderCartContent, new ThymeleafTemplateEngine());

        get("/checkout", OrderControllerDB::renderCheckoutPage, new ThymeleafTemplateEngine());
        post("/checkout", OrderControllerDB::saveShippingInfoToSession);

        get("/pay", OrderControllerDB::renderPaymentPage, new ThymeleafTemplateEngine());
        post("/pay", OrderControllerDB::saveBankCardData);

        get("/aboutus", ProductControllerDB::renderAboutUs, new ThymeleafTemplateEngine());
        get("*", (req, res) -> {
            throw new Exception("Exceptions everywhere!");
        });
        // Add this line to your project to enable the debug screen
    }

}
