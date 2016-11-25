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

        get("/add/:id", ProductControllerDB::addToCart);
        get("/remove/:id", ProductControllerDB :: removeFromCart);
        get("/", ProductControllerDB::renderProducts, new ThymeleafTemplateEngine());
        get("/hello", (req, res) -> "Hello World");
        get("/category/:categoryid", ProductControllerDB::renderProducts, new ThymeleafTemplateEngine());
        get("/supplier/:supplierid", ProductControllerDB::renderProducts, new ThymeleafTemplateEngine());
        get("/cartcontent", ProductControllerDB::renderCartContent, new ThymeleafTemplateEngine());
        get("/aboutus", ProductControllerDB::renderAboutUs, new ThymeleafTemplateEngine());
        get("*", (req, res) -> {
            throw new Exception("Exceptions everywhere!");
        });
        // Add this line to your project to enable the debug screen
    }

}
