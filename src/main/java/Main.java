import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;
import com.codecool.shop.testdata.TestData;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        TestData.populateData();

        get("/add/:id", ProductController::addToCart);
        get("/remove/:id", ProductController :: removeFromCart);
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());
        get("/hello", (req, res) -> "Hello World");
        get("/category/:categoryid", ProductController::renderProducts, new ThymeleafTemplateEngine());
        get("/supplier/:supplierid", ProductController::renderProducts, new ThymeleafTemplateEngine());
        get("/cartcontent", ProductController::renderCartContent, new ThymeleafTemplateEngine());
        get("*", (req, res) -> {
            throw new Exception("Exceptions everywhere!");
        });
        // Add this line to your project to enable the debug screen
        enableDebugScreen();
    }

}
