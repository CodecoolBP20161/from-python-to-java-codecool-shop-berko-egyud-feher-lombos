import com.codecool.shop.controller.event_controller.session_controller.SessionEventController;
import com.codecool.shop.controller.rendered_page.AboutUsControllerDB;
import com.codecool.shop.controller.rendered_page.OrderControllerDB;
import com.codecool.shop.controller.rendered_page.ProductControllerDB;
import com.codecool.shop.controller.rendered_page.UserController;
import com.codecool.shop.dao.implementation.db.AbstractDBHandler;
import com.codecool.shop.testdata.TestDataDB;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;

public class Main {

    static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws NotFoundException {
        LOGGER.info("Server is running.");

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        TestDataDB.populateData();

        before( "*", (request, response) ->
        {
            AbstractDBHandler.getConnection();
            if (request.session().attribute("authenticated") == null) {
                request.session().attribute("authenticated", false);
                request.session().attribute("username", false);
            }
        });

        // --- ROUTES ---
        // Index page
        get("/", ProductControllerDB::renderProducts, new ThymeleafTemplateEngine());

        //  UserController's method and rendered action
        get("/signin", UserController::renderSignIn, new ThymeleafTemplateEngine());
        get("/signup", UserController::renderSignUp, new ThymeleafTemplateEngine());
        get("/logout", UserController::logOut, new ThymeleafTemplateEngine());
        post("/signin", UserController::login, new ThymeleafTemplateEngine());
        post("/signup", UserController::createUser, new ThymeleafTemplateEngine());

        // SessionEventController's methods
        get("/add/:id", SessionEventController::addToSessionCart);
        get("/remove/:id", SessionEventController::removeFromSessionCart);
        get("/removeall", SessionEventController::removeAllFromSessionCart);

        // ProductController's render actions
        get("/category/:categoryid", ProductControllerDB::renderFilteredProducts, new ThymeleafTemplateEngine());
        get("/supplier/:supplierid", ProductControllerDB::renderFilteredProducts, new ThymeleafTemplateEngine());
        get("/cartcontent", OrderControllerDB::renderCartContent, new ThymeleafTemplateEngine());

        // OrderController's render actions
        get("/checkout", OrderControllerDB::renderCheckoutPage, new ThymeleafTemplateEngine());
        post("/checkout", SessionEventController::saveShippingInfoToSession);
        get("/pay", OrderControllerDB::renderPaymentPage, new ThymeleafTemplateEngine());
        post("/pay", SessionEventController::saveBankCardDataToSession);
        get("/shippinginformation", OrderControllerDB::renderShippingInformationPage, new ThymeleafTemplateEngine());
        get("/afterpay", OrderControllerDB::renderAfterPaymentPage, new ThymeleafTemplateEngine());


        get("/aboutus", AboutUsControllerDB::renderAboutUs, new ThymeleafTemplateEngine());

        get("*", (req, res) -> {
            throw new Exception("Exceptions everywhere!");
        });
    }

}
