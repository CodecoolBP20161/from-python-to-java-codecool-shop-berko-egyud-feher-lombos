package com.codecool.shop.controller.event_controller.session_controller;


import com.codecool.shop.dao.implementation.db.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.db.ProductDaoDB;
import com.codecool.shop.dao.implementation.db.ShippingDataDB;
import com.codecool.shop.dao.implementation.db.SupplierDaoDB;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import com.codecool.shop.model.ProductCategory;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SessionEventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionEventController.class);

    private static ProductDaoDB productDB = ProductDaoDB.getInstance();
    private static ProductCategoryDaoDB productCategoryDB = ProductCategoryDaoDB.getInstance();
    private static SupplierDaoDB supplierDB = SupplierDaoDB.getInstance();
    private static ShippingDataDB shippingDataDB = ShippingDataDB.getInstance();



    /**
     * Handle the content of the params Hashmap
     * @param req - Spark Request
     * @return all products
     * @throws NotFoundException
     * @throws SQLException
     */
    public static Map setParams(Request req) throws NotFoundException, SQLException {
        LOGGER.info("setParams() method is called.");

        Map params = new HashMap<>();

        params.put("category", new ProductCategory("All Products", "All Products", "All Products"));
        params.put("categories", productCategoryDB.getAll());
        params.put("suppliers", supplierDB.getAll());
        params.put("products", productDB.getAll());

        req.session().attribute("currentUrl", "/");

        if ( req.url().equals("http://localhost:8888/cartcontent")) {
            req.session().attribute("currentUrl", "/cartcontent");
        }
        if ( req.url().equals("http://localhost:8888/shippinginformation")) {
            req.session().attribute("currentUrl", "/shippinginformation");
        }

        Order cart = req.session().attribute("Cart");
        params.put("cart", cart);
        return params;
    }


    /**
     * Handle the content of the session and set the variables of Order object.
     * Add the item with the given id to the cart.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return null
     * @throws NotFoundException
     */
    public static String addToSessionCart(Request req, Response res) throws NotFoundException {
        LOGGER.info("addToSessionCart() method is called.");

        int id = Integer.parseInt(req.params(":id"));
        Orderable order;

        if (req.session().attribute("Cart") == null) {
            order = new Order();
            LOGGER.debug("Session's cart EMPTY, then Order object created.");
        } else {
            order = req.session().attribute("Cart");
            LOGGER.debug("Session's cart NOT EMPTY, then Order object called from session.");
        }

        order.add(productDB.find(id));
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }

    /**
     * Handle the content of the session and set the variables of Order object.
     * Remove the item with the given id from the cart.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return null
     * @throws NotFoundException
     */
    public static String removeFromSessionCart(Request req, Response res) throws NotFoundException {
        LOGGER.info("removeFromSessionCart() method is called.");

        int id = Integer.parseInt(req.params(":id"));
        Orderable order;
        order = req.session().attribute("Cart");

        // remove one product from session
        order.remove(productDB.find(id));
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }

    /**
     * Handle the content of the session and set the variables of Order object.
     * Remove all items from the cart.
     * @param req - Spark Request
     * @param res - Spark Response
     * @return null
     * @throws NotFoundException
     */
    public static String removeAllFromSessionCart(Request req, Response res) throws NotFoundException {
        LOGGER.info("removeAllFromSessionCart() method is called.");

        Order order = null;
        req.session().attribute("Cart", order);
        res.redirect("/");
        return null;
    }


    /**
     * Save shipping data for order to session
     * @param req - Spark Request
     * @param res - Spark Response
     * @return null
     */
    public static String saveShippingInfoToSession(Request req, Response res) throws NotFoundException, SQLException {
        LOGGER.info("saveShippingInfoToSession() method is called.");

        String first_name = req.queryParams("first_name");
        String last_name = req.queryParams("last_name");
        String email = req.queryParams("email");
        String phone = req.queryParams("phone");
        String address = req.queryParams("adress");
        String city = req.queryParams("city");
        String state = req.queryParams("state");
        String zip = req.queryParams("zip");
        String comment = req.queryParams("comment");

        ArrayList<String> shippingDataList = new ArrayList(Arrays.asList(first_name, last_name, email, phone, address, city,state, zip, comment));
        req.session().attribute("ShippingDataList", shippingDataList);

        // saving shipping data for order
        Orderable order;
        order = req.session().attribute("Cart");
        shippingDataDB.add(shippingDataList, (Order) order);
        LOGGER.debug("shippingDataList added to shippingDataDB");

        res.redirect("/shippinginformation");
        return null;
    }

    /**
     * Save bankcard data to the session
     * @param req - Spark Request
     * @param res - Spark Response
     * @return null
     */
    public static String saveBankCardDataToSession(Request req, Response res) {
        LOGGER.info("saveBankCardDataToSession() method is called.");

        String card_holder_name = req.queryParams("card-holder-name");
        String card_number = req.queryParams("card-number");
        String expiry_month = req.queryParams("expiry-month");
        String expiry_year = req.queryParams("expiry-year");
        String cvv = req.queryParams("cvv");

        ArrayList<String> bankCardDataList =new ArrayList(Arrays.asList(card_holder_name, card_number, expiry_month, expiry_year, cvv));
        req.session().attribute("BankCardDataList", bankCardDataList);

        res.redirect("/afterpay");
        return null;
    }
}
