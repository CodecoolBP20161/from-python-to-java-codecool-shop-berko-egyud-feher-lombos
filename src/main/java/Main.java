import com.codecool.shop.controller.AboutUsControllerDB;
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

        get("/", ProductControllerDB::renderProducts, new ThymeleafTemplateEngine());

        // Controller's methods
        get("/add/:id", Controller::addToSessionCart);
        get("/remove/:id", Controller ::removeFromSessionCart);
        get("/removeall", Controller ::removeAllFromSessionCart);

        // ProductController's render actions
        get("/category/:categoryid", ProductControllerDB::renderFilteredProducts, new ThymeleafTemplateEngine());
        get("/supplier/:supplierid", ProductControllerDB::renderFilteredProducts, new ThymeleafTemplateEngine());
        get("/cartcontent", OrderControllerDB::renderCartContent, new ThymeleafTemplateEngine());

        // OrderController's render actions
        get("/checkout", OrderControllerDB::renderCheckoutPage, new ThymeleafTemplateEngine());
        post("/checkout", Controller::saveShippingInfoToSession);
        get("/pay", OrderControllerDB::renderPaymentPage, new ThymeleafTemplateEngine());
        post("/pay", Controller::saveBankCardDataToSession);
        get("/afterpay", OrderControllerDB::renderAfterPaymentPage, new ThymeleafTemplateEngine());


        get("/aboutus", AboutUsControllerDB::renderAboutUs, new ThymeleafTemplateEngine());
        get("*", (req, res) -> {
            throw new Exception("Exceptions everywhere!");
        });
        // Add this line to your project to enable the debug screen
    }

}
