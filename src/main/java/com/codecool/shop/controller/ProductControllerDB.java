package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.db.*;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Orderable;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.process.CheckoutProcess;
import javassist.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProductControllerDB {

    private static ProductDaoDB ProductDB = new ProductDaoDB();
    private static OrderDaoDB OrderDaoDB = new OrderDaoDB();
    private static LineItemDaoDB LineItemDaoDB = new LineItemDaoDB();
    private static ProductCategoryDaoDB ProductCategoryDB = new ProductCategoryDaoDB();
    private static SupplierDaoDB supplierDB = new SupplierDaoDB();

    static Integer categoryId = 1;
    static Integer supplierId = 1;
    static ArrayList<String> savedOrderExamine;
    private static String STATUS_CHECKED_CART = "UNCHECKED";

    // Handle the content of the params HashMap
    private static Map setParams(Request req) throws NotFoundException, SQLException {

        Map params = new HashMap<>();

        params.put("category", new ProductCategory("All Products", "All Products", "All Products"));
        params.put("categories", ProductCategoryDB.getAll());
        params.put("products", ProductDB.getAll());
        params.put("suppliers", supplierDB.getAll());

        req.session().attribute("currentUrl", "/");

        if ( req.params(":categoryid") != null ) {
            categoryId = Integer.parseInt(req.params(":categoryid"));
            params.put("products", ProductDB.getBy(ProductCategoryDB.find(categoryId)));
            req.session().attribute("currentUrl", "/category/" + req.params(":categoryid"));

        }
        else if ( req.params(":supplierid") != null ) {
            supplierId = Integer.parseInt(req.params(":supplierid"));
            params.put("products", ProductDB.getBy(supplierDB.find(supplierId)));
            req.session().attribute("currentUrl", "/supplier/" + req.params(":supplierid"));
        }
        else if ( req.url().equals("http://localhost:8888/cartcontent")) {
            req.session().attribute("currentUrl", "/cartcontent");
        }

        Order cart = req.session().attribute("Cart");
        params.put("cart", cart);
        return params;

    }

    // Action for display all or filtered products
    public static ModelAndView renderProducts(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);

        if ( req.params(":categoryid") != null ) {
            params.put("category", ProductCategoryDB.find(categoryId));
        }
        if ( req.params(":supplierid") != null ) {
            params.put("supplier", supplierDB.find(supplierId));
        }
        return new ModelAndView(params, "product/index");
    }

    // Action for display cart content
    public static ModelAndView renderCartContent(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);
        return new ModelAndView(params, "product/cart");
    }

    // Action for display about us page
    public static ModelAndView renderAboutUs(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);
        return new ModelAndView(params, "product/aboutus");
    }

    // Handle the content of the session and set the variables of Order object
    public static String addToCart(Request req, Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        Orderable order;

        if (req.session().attribute("Cart") == null) {
            order = new Order();
        } else {
            order = req.session().attribute("Cart");
        }

        order.add(ProductDB.find(id));
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }
    // Handle the content of the session and set the variables of Order object
    public static String removeFromCart(Request req, Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        Orderable order;

        order = req.session().attribute("Cart");

        // remove one product from session
        order.remove(ProductDB.find(id));
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }

    // Handle the content of the session and set the variables of Order object
    public static String removeAllFromCart(Request req, Response res) throws NotFoundException {
        Order order = null;
        req.session().attribute("Cart", order);
        res.redirect(req.session().attribute("currentUrl"));
        return null;
    }

    public static ModelAndView renderCheckoutProcess(Request req, Response res) throws NotFoundException, SQLException {
        Map params = setParams(req);
        Order order = req.session().attribute("Cart");

        // set order's status to "CHECKED"
        CheckoutProcess checkoutProcess = new CheckoutProcess();
        checkoutProcess.process(order);

        return new ModelAndView(params, "product/checkout");
    }
}